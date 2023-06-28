package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.dto.MedicalHistoryDTO;
import com.example.MiHistoriaClinica.dto.PatientLoginDTO;
import com.example.MiHistoriaClinica.dto.PatientDTO;
import com.example.MiHistoriaClinica.dto.TurnoDTO;
import com.example.MiHistoriaClinica.exception.PatientNotFoundException;
import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.MedicineModel;
import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.repository.MedicRepository;
import com.example.MiHistoriaClinica.repository.MedicineRepository;
import com.example.MiHistoriaClinica.repository.PatientRepository;
import com.example.MiHistoriaClinica.repository.CustomRepositoryAccess;
import com.example.MiHistoriaClinica.service.interfaces.PatientService;
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


    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, CustomRepositoryAccess customRepositoryAccess, MedicineRepository medicineRepository, MedicRepository medicRepository) {
        this.patientRepository = patientRepository;
        this.customRepositoryAccess = customRepositoryAccess;
        this.medicineRepository = medicineRepository;
        this.medicRepository = medicRepository;
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

    public void addTurno(Long patientId, Long medicId, TurnoDTO request) {
        PatientModel patient = patientRepository.findById(patientId).get();
        MedicModel medic = medicRepository.findById(medicId).get();

        customRepositoryAccess.addTurno(patient, medic, request);

    }


}


