package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.presentation.dto.MedicalHistoryDTO;
import com.example.MiHistoriaClinica.presentation.dto.PatientLoginDTO;
import com.example.MiHistoriaClinica.presentation.dto.PatientDTO;
import com.example.MiHistoriaClinica.presentation.dto.TurnoDTO;
import com.example.MiHistoriaClinica.exception.PatientNotFoundException;
import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.persistence.model.MedicModel;
import com.example.MiHistoriaClinica.persistence.model.MedicineModel;
import com.example.MiHistoriaClinica.persistence.model.PatientModel;
import com.example.MiHistoriaClinica.persistence.model.Turnos;
import com.example.MiHistoriaClinica.persistence.repository.*;
import com.example.MiHistoriaClinica.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final CustomRepositoryAccess customRepositoryAccess;
    private final MedicineRepository medicineRepository;
    private final MedicRepository medicRepository;
    private final TurnosRepository turnosRepository;


    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, CustomRepositoryAccess customRepositoryAccess, MedicineRepository medicineRepository, MedicRepository medicRepository, TurnosRepository turnosRepository) {
        this.patientRepository = patientRepository;
        this.customRepositoryAccess = customRepositoryAccess;
        this.medicineRepository = medicineRepository;
        this.medicRepository = medicRepository;
        this.turnosRepository = turnosRepository;
    }


    /**
     * método para generar el código de enlace y actualizar el registro del paciente con el nuevo código
     * @param patientId
     * @return linkCode
     */
    public String generateLinkCode(Long patientId) {
        PatientModel patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            throw new RuntimeException("No se pudo generar el código de enlace. El paciente no existe.");
        }
        String linkCode = UUID.randomUUID().toString().substring(0, 4); // Generar un código aleatorio de 4 caracteres
        patient.setLinkCode(linkCode);
        patientRepository.save(patient);
        return linkCode;
    }


    @Override
    public PatientModel createPatient(PatientDTO patient) {
        return customRepositoryAccess.saveDTO(patient);
    }


    @Override
    public PatientModel loginPatient(PatientLoginDTO patient) {
        PatientModel result = patientRepository.findByDniAndPassword(patient.getDni(), patient.getPassword());
        if (result == null) {
            throw new PatientNotFoundException();
        } else {
            return result;
        }
    }


    @Override
    public List<MedicModel> getMedicsByPatientId(Long id) {
        return patientRepository.getMedicsByPatientId(id);
    }


    @Override
    public List<MedicineModel> getMedicinesByPatientId(Long id) {
        return patientRepository.getMedicinesByPatientId(id);
    }

    public List<MedicineModel> getMedicinesByStatus(Long id, String status) {
        return patientRepository.getMedicinesByPatientIdAndStatus(id, status);
    }

    @Override
    public PatientModel updatePatient(Long id, PatientModel newPatient) {
        PatientModel patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        patient.setName(newPatient.getName());
        patient.setLastname(newPatient.getLastname());
        patient.setEmail(newPatient.getEmail());
        patient.setPassword(newPatient.getPassword());
        patient.setDni(newPatient.getDni());
        patient.setBirthdate(newPatient.getBirthdate());

        return patientRepository.save(patient);
    }


    @Override
    public MedicalHistoryDTO getMedicalHistory(Long id){
        PatientModel thisPatient = patientRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        return new MedicalHistoryDTO(thisPatient.getMedicalHistory());
    }


    @Override
    public MedicineModel getMedicineByMedicineId(Long medicineId) {

        Optional<MedicineModel> medicine = medicineRepository.findById(medicineId);
        if (medicine.isPresent())           return medicine.get();
        else                                return null;

    }


    @Override
    public void saveMedicine(MedicineModel medicine) {
        medicineRepository.save(medicine);
    }






    @Override
    public PatientModel getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }

    @Override
    public PatientModel getPatientByDni(Long dni) {
        PatientModel patient = patientRepository.findByDni(dni);
        if (patient == null) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        } else {
            return patient;
        }
    }

    @Override
    public PatientDTO getPatientInfo(Long patientId) {
        ModelMapper modelMapper = new ModelMapper();
        PatientModel patientModel = getPatientById(patientId);
        return modelMapper.map(patientModel, PatientDTO.class);
    }

    public void createTurno(Long patientId, Long medicId, TurnoDTO request, String medicalCenter) {
        PatientModel patient = patientRepository.findById(patientId).get();
        MedicModel medic = medicRepository.findById(medicId).get();

        customRepositoryAccess.createTurno(patient, medic, request, medicalCenter);

    }


    public List<Turnos> getMisTurnos(Long id) {
        PatientModel patient = patientRepository.findById(id).get();

        return turnosRepository.findByPatient(patient);
    }

    public void deleteTurno(Long id) {
        turnosRepository.deleteById(id);
    }
}

