package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.model.Turnos;
import com.example.MiHistoriaClinica.persistence.model.Analysis;

public interface EmailService {
    
    void sendTurnoReminderEmail(Patient patient, Turnos turno);
    
    void sendAnalysisReminderEmail(Patient patient, Analysis analysis);
    
    void sendTurnoConfirmationEmail(Patient patient, Turnos turno);
    
    void sendAnalysisConfirmationEmail(Patient patient, Analysis analysis);
} 