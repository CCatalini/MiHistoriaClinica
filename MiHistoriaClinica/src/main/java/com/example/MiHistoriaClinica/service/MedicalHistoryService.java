package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.MedicalHistory;

public interface MedicalHistoryService {
    MedicalHistory getMedicalHistoryById(Long id);

    byte[] parseMedicalHistoryToPDF(MedicalHistory medicalHistory);
}
