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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import com.example.MiHistoriaClinica.service.EmailService;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final CustomRepositoryAccess customRepositoryAccess;
    private final MedicineRepository medicineRepository;
    private final MedicRepository medicRepository;
    private final TurnosRepository turnosRepository;
    private final EmailService emailService;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, CustomRepositoryAccess customRepositoryAccess, MedicineRepository medicineRepository, MedicRepository medicRepository, TurnosRepository turnosRepository, EmailService emailService) {
        this.patientRepository = patientRepository;
        this.customRepositoryAccess = customRepositoryAccess;
        this.medicineRepository = medicineRepository;
        this.medicRepository = medicRepository;
        this.turnosRepository = turnosRepository;
        this.emailService = emailService;
    }

    /**
     * Method para generar el código de enlace y actualizar el registro del paciente con el nuevo código
     *
     * @return linkCode
     */
    public String generateLinkCode(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            throw new RuntimeException("No se pudo generar el código de enlace. El paciente no existe.");
        }

        // código numérico de 4 dígitos
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        String linkCode = String.valueOf(code);

        patient.setLinkCode(linkCode);
        patientRepository.save(patient);
        return linkCode;
    }

    @Override
    public Patient createPatient(PatientDTO patient) {
        // Crear el paciente
        Patient newPatient = customRepositoryAccess.saveDTO(patient);
        
        // Generar token de verificación
        String verificationToken = UUID.randomUUID().toString();
        newPatient.setVerificationToken(verificationToken);
        newPatient.setEmailVerified(false);
        newPatient.setEnabled(false);
        
        // Guardar con el token
        newPatient = patientRepository.save(newPatient);
        
        // Enviar email de verificación
        String verificationUrl = "http://localhost:4200/patient/verify?token=" + verificationToken;
        try {
            emailService.sendVerificationEmail(newPatient, verificationUrl);
        } catch (Exception e) {
            // Log pero no fallar el registro
            System.err.println("Error enviando email de verificación: " + e.getMessage());
        }
        
        return newPatient;
    }
    
    public Patient verifyEmail(String token) {
        Patient patient = patientRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Token de verificación inválido o expirado"));
        
        if (patient.isEmailVerified()) {
            throw new RuntimeException("El email ya ha sido verificado");
        }
        
        patient.setEmailVerified(true);
        patient.setEnabled(true);
        patient.setVerificationToken(null);
        
        patient = patientRepository.save(patient);
        
        // Enviar email de bienvenida
        try {
            emailService.sendWelcomeEmail(patient);
        } catch (Exception e) {
            System.err.println("Error enviando email de bienvenida: " + e.getMessage());
        }
        
        return patient;
    }

    @Override
    public Patient loginPatient(PatientLoginDTO patient) {
        Patient result = patientRepository.findByDniAndPassword(patient.getDni(), patient.getPassword());
        if (result == null) {
            throw new PatientNotFoundException();
        }
        
        // Verificar que el email esté verificado
        if (!result.isEmailVerified()) {
            throw new RuntimeException("Debes verificar tu email antes de iniciar sesión. Revisa tu correo electrónico.");
        }
        
        if (!result.isEnabled()) {
            throw new RuntimeException("Tu cuenta no está activa. Por favor contacta al soporte.");
        }
        
        return result;
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
    public MedicalFileDTO getMedicalHistory(Long id) {
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
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
        Medic medic = medicRepository.findById(medicId)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado"));
        customRepositoryAccess.createTurno(patient, medic, request, medicalCenter);
    }

    @Override
    public List<Turnos> getMisTurnos(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
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


