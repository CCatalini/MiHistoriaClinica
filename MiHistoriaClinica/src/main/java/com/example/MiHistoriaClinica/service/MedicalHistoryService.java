package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.MedicalHistory;

import java.util.List;

public interface MedicalHistoryService {
    MedicalHistory getMedicalHistoryById(Long id);

    byte[] parseMedicalHistoryToPDF(MedicalHistory medicalHistory);

    List<MedicalHistory> findAll();
}
