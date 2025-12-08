package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.*;

public interface EmailService {
    
    void sendVerificationEmail(Patient patient, String verificationUrl);
    
    void sendWelcomeEmail(Patient patient);
    
    void sendMedicVerificationEmail(Medic medic, String verificationUrl);
    
    void sendMedicWelcomeEmail(Medic medic);
    
    // Emails de turnos
    void sendTurnoConfirmationEmail(Patient patient, Turnos turno);
    
    void sendTurnoReminderEmail(Patient patient, Turnos turno);
    
    void sendTurnoCancellationEmail(Patient patient, Turnos turno);
    
    // Emails de estudios/análisis
    void sendAnalysisReminderEmail(Patient patient, Analysis analysis);
    
    void sendAnalysisScheduledEmail(Patient patient, Analysis analysis);
    
    // Email resumen de consulta médica
    void sendConsultationSummaryEmail(Patient patient, Medic medic, 
                                      java.util.List<String> estudios, 
                                      java.util.List<String> medicamentos, 
                                      boolean historiaActualizada);
}

