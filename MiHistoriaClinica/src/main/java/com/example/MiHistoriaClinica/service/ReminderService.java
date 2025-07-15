package com.example.MiHistoriaClinica.service;

public interface ReminderService {
    
    void sendTurnoReminders();
    
    void sendAnalysisReminders();
    
    void scheduleReminderJobs();
} 