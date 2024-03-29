package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.presentation.dto.MedicalFileDTO;
import com.example.MiHistoriaClinica.presentation.dto.PatientLoginDTO;
import com.example.MiHistoriaClinica.presentation.dto.PatientDTO;
import com.example.MiHistoriaClinica.presentation.dto.TurnoDTO;
import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import com.example.MiHistoriaClinica.util.exception.PatientNotFoundException;
import com.example.MiHistoriaClinica.util.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.Medicine;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.model.Turnos;
import com.example.MiHistoriaClinica.persistence.repository.*;
import com.example.MiHistoriaClinica.service.PatientService;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private final JwtGenerator jwt = new JwtGeneratorImpl();


    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, CustomRepositoryAccess customRepositoryAccess, MedicineRepository medicineRepository, MedicRepository medicRepository, TurnosRepository turnosRepository, EmailService emailService) {
        this.patientRepository = patientRepository;
        this.customRepositoryAccess = customRepositoryAccess;
        this.medicineRepository = medicineRepository;
        this.medicRepository = medicRepository;
        this.turnosRepository = turnosRepository;
    }

    /**
     * Método para generar el código de enlace y actualizar el registro del paciente con el nuevo código
     * @return linkCode
     */
    public String generateLinkCode(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            throw new RuntimeException("No se pudo generar el código de enlace. El paciente no existe.");
        }
        String linkCode = UUID.randomUUID().toString().substring(0, 4); // Generar un código aleatorio de 4 caracteres
        patient.setLinkCode(linkCode);
        patientRepository.save(patient);
        return linkCode;
    }


/*
    public ResponseEntity<String> confirmAccount(Long patientId) {
        patientRepository.setEmailConfirmed(patientId, true);
        return null;
    }



 */

    @Override
    public Patient createPatient(PatientDTO patient) {
        return customRepositoryAccess.saveDTO(patient);
    }

    @Override
    public Patient loginPatient(PatientLoginDTO patient) {
        Patient result = patientRepository.findByDniAndPassword(patient.getDni(), patient.getPassword());
        if (result == null) {
            throw new PatientNotFoundException();
        } else {
            return result;
        }
    }

    @Override
    public List<Medic> getMedicsByPatientId(Long id) {
        return patientRepository.getMedicsByPatientId(id);
    }

    @Override
    public List<Medicine> getMedicinesByPatientId(Long id) {
        return patientRepository.getMedicinesByPatientId(id);
    }

    public List<Medicine> getMedicinesByStatus(Long id, String status) {
        return patientRepository.getMedicinesByPatientIdAndStatus(id, status);
    }

    @Override
    public Patient updatePatient(Long id, Patient newPatient) {
        Patient patient = patientRepository.findById(id)
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
    public MedicalFileDTO getMedicalHistory(Long id){
        Patient thisPatient = patientRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return new MedicalFileDTO(thisPatient.getMedicalFile());
    }

    @Override
    public Medicine getMedicineByMedicineId(Long medicineId) {
        Optional<Medicine> medicine = medicineRepository.findById(medicineId);
        return medicine.orElse(null);
    }

    @Override
    public void saveMedicine(Medicine medicine) {
        medicineRepository.save(medicine);
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }

    @Override
    public Patient getPatientByDni(Long dni) {
        Patient patient = patientRepository.findByDni(dni);
        if (patient == null) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        } else {
            return patient;
        }
    }

    @Override
    public PatientDTO getPatientInfo(Long patientId) {
        ModelMapper modelMapper = new ModelMapper();
        Patient patient = getPatientById(patientId);
        return modelMapper.map(patient, PatientDTO.class);
    }

    @Override
    public void createTurno(Long patientId, Long medicId, TurnoDTO request, String medicalCenter) {
        Patient patient = patientRepository.findById(patientId).get();
        Medic medic = medicRepository.findById(medicId).get();
        customRepositoryAccess.createTurno(patient, medic, request, medicalCenter);
    }

    @Override
    public List<Turnos> getMisTurnos(Long id) {
        Patient patient = patientRepository.findById(id).get();
        return turnosRepository.findByPatient(patient);
    }

    @Override
    public void deleteTurno(Long id) {
        turnosRepository.deleteById(id);
    }

    @Override
    public List<Medic> getMedicsBySpecialty(Long id, String specialty) {
        MedicalSpecialtyE medicalSpecialtyE = MedicalSpecialtyE.getEnumFromName(specialty);
        return patientRepository.getMedicsBySpecialty(id, medicalSpecialtyE);
    }


}


