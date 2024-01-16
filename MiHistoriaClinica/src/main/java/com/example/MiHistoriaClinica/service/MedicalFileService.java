package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.MedicalFile;

import java.util.List;

public interface MedicalFileService {
    MedicalFile getMedicalHistoryById(Long id);

    byte[] parseMedicalHistoryToPDF(MedicalFile medicalFile);

    List<MedicalFile> findAll();
}
