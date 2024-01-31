package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.MedicalFile;

import java.util.List;

public interface MedicalFileService {
    MedicalFile getMedicalFileById(Long id);

    List<MedicalFile> findAll();

    List<String> getBloodTypes();
}
