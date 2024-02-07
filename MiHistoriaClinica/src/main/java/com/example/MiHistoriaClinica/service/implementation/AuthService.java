package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.repository.CustomRepositoryAccess;
import com.example.MiHistoriaClinica.persistence.repository.PatientRepository;
import com.example.MiHistoriaClinica.presentation.dto.PatientDTO;
import com.example.MiHistoriaClinica.presentation.dto.TokenDTO;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final CustomRepositoryAccess customRepositoryAccess;
    private final PatientRepository patientRepository;
    private final EmailService emailService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);

    public AuthService(CustomRepositoryAccess customRepositoryAccess, PatientRepository patientRepository, EmailService emailService) {
        this.customRepositoryAccess = customRepositoryAccess;
        this.patientRepository = patientRepository;
        this.emailService = emailService;
    }


    public void registerPatient(PatientDTO patientDTO) {
        customRepositoryAccess.saveDTO(patientDTO);
        String token = String.valueOf(jwt.generateTokenWithEmail(patientDTO.getEmail(), "PATIENT", false));
        String confirmationLink = "http://localhost:8080/auth/patient/confirm-account";

        emailService.sendConfirmationEmail(patientDTO.getEmail(), confirmationLink, token);
    }


    public boolean confirmAccount(String token) {
        try {
            String email = jwtValidator.getEmail(token);
            Patient patient = patientRepository.findByEmail(email);
            // Set emailConfirmed to true and save patient
            return patient != null;
        } catch (InvalidTokenException e) {
            // Log or print the exception for debugging purposes
            e.printStackTrace();
            return false;
        }
    }



    public TokenDTO generateTokenWithEmail(String email, String patient, boolean b) {
        return jwt.generateTokenWithEmail(email, patient, b);
    }
}
