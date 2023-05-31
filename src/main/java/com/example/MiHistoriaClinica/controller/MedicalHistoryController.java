package com.example.MiHistoriaClinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;
        import com.example.MiHistoriaClinica.model.MedicalHistoryModel;
        import com.example.MiHistoriaClinica.repository.MedicalHistoryRepository;

import java.util.List;

// todo check Cami

@RestController
@RequestMapping("/medical-history")
public class MedicalHistoryController {
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    public MedicalHistoryController(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    @GetMapping("/getAll")
    public List<MedicalHistoryModel> getAllMedicalHistory() {
        return medicalHistoryRepository.findAll();
    }
}
