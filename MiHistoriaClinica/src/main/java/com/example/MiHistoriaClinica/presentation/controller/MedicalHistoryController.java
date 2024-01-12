package com.example.MiHistoriaClinica.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;
        import com.example.MiHistoriaClinica.persistence.model.MedicalHistoryModel;
        import com.example.MiHistoriaClinica.persistence.repository.MedicalHistoryRepository;

import java.util.List;

// todo check Cami

@RestController
@RequestMapping("/medical-history")
public class MedicalHistoryController {
    private final MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    public MedicalHistoryController(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    @GetMapping("/getAll")
    public List<MedicalHistoryModel> getAllMedicalHistory() {
        return medicalHistoryRepository.findAll();
    }
}
