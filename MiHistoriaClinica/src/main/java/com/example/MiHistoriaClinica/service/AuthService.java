package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.repository.CustomRepositoryAccess;
import com.example.MiHistoriaClinica.persistence.repository.PatientRepository;
import com.example.MiHistoriaClinica.presentation.dto.PatientDTO;
import com.example.MiHistoriaClinica.service.implementation.EmailService;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final CustomRepositoryAccess customRepositoryAccess;
    private final PatientRepository patientRepository;
    private final EmailService emailService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();

    public AuthService(CustomRepositoryAccess customRepositoryAccess, PatientRepository patientRepository, EmailService emailService) {
        this.customRepositoryAccess = customRepositoryAccess;
        this.patientRepository = patientRepository;
        this.emailService = emailService;
    }


    public Patient registerPatient(PatientDTO patientDTO) {
        Patient patient = customRepositoryAccess.saveDTO(patientDTO);
        String token = String.valueOf(jwt.generateTokenWithEmail(patientDTO.getEmail(), "PATIENT", false));
        String confirmationLink = "http://localhost:8080/auth/patient/confirm-account";

        emailService.sendConfirmationEmail(patientDTO.getEmail(), confirmationLink, token);
        return patient;
    }


    public boolean confirmAccount(String token) {
        String email = jwt.getEmailFromToken(token);
        Patient patient = patientRepository.findByEmail(email);
        if (patient != null) {
            patient.setEmailConfirmed(true);
            patientRepository.save(patient);
            return true;
        }
        return false;
    }


}
