package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.presentation.dto.MedicalHistoryDTO;
import com.example.MiHistoriaClinica.service.MedicalHistoryService;
import com.example.MiHistoriaClinica.service.PatientService;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import com.example.MiHistoriaClinica.persistence.model.MedicalHistory;
        import com.example.MiHistoriaClinica.persistence.repository.MedicalHistoryRepository;

import java.util.List;


@RestController
@RequestMapping("/medical-history")
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;
    private final PatientService patientService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);

    @Autowired
    public MedicalHistoryController(MedicalHistoryService medicalHistoryService, PatientService patientService) {
        this.medicalHistoryService = medicalHistoryService;
        this.patientService = patientService;
    }

    @GetMapping("/getAll")
    public List<MedicalHistory> getAllMedicalHistory() {
        return medicalHistoryService.findAll();
    }

    @GetMapping("/get-medical-history")
    public ResponseEntity<MedicalHistoryDTO> getMedicalHistory(@RequestHeader("Authorization") String token ) throws InvalidTokenException {
        MedicalHistoryDTO medicalHistory = patientService.getMedicalHistory(jwtValidator.getId(token));
        return new ResponseEntity<>(medicalHistory, HttpStatus.OK);
    }

    @GetMapping(value = "/download-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadMedicalHistory(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        Long id = jwtValidator.getId(token);
        MedicalHistory medicalHistory = medicalHistoryService.getMedicalHistoryById(id);

        byte[]  pdfBytes = medicalHistoryService.parseMedicalHistoryToPDF(medicalHistory);

        // Devolver la respuesta con el contenido del PDF y las cabeceras necesarias
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=historia_clinica.pdf")
                .body(pdfBytes);
    }
}
