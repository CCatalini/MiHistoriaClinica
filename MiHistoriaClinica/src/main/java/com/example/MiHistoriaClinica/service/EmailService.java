package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.model.Medic;

public interface EmailService {
    
    void sendVerificationEmail(Patient patient, String verificationUrl);
    
    void sendWelcomeEmail(Patient patient);
    
    void sendMedicVerificationEmail(Medic medic, String verificationUrl);
    
    void sendMedicWelcomeEmail(Medic medic);
}

