package com.example.MiHistoriaClinica.service;

public interface MedicalHistoryService {

    byte[] createPdf(Long id, boolean includeMedicalFile, boolean includeAnalysis, boolean includeMedications, boolean includeAppointments);
}
