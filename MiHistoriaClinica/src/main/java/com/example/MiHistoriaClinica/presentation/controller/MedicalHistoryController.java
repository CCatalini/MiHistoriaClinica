package com.example.MiHistoriaClinica.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;
        import com.example.MiHistoriaClinica.persistence.model.MedicalHistory;
        import com.example.MiHistoriaClinica.persistence.repository.MedicalHistoryRepository;

import java.util.List;


@RestController
@RequestMapping("/medical-history")
public class MedicalHistoryController {
    private final MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    public MedicalHistoryController(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    @GetMapping("/getAll")
    public List<MedicalHistory> getAllMedicalHistory() {
        return medicalHistoryRepository.findAll();
    }
}
