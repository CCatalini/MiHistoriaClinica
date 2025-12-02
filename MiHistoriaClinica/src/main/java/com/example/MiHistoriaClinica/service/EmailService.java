package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.Turnos;

public interface EmailService {
    
    void sendVerificationEmail(Patient patient, String verificationUrl);
    
    void sendWelcomeEmail(Patient patient);
    
    void sendMedicVerificationEmail(Medic medic, String verificationUrl);
    
    void sendMedicWelcomeEmail(Medic medic);
    
    // Emails de turnos
    void sendTurnoConfirmationEmail(Patient patient, Turnos turno);
    
    void sendTurnoReminderEmail(Patient patient, Turnos turno);
    
    void sendTurnoCancellationEmail(Patient patient, Turnos turno);
}

