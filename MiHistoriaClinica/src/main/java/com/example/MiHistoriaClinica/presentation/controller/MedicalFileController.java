package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.service.implementation.MedicServiceImpl;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.persistence.model.MedicalFile;
import com.example.MiHistoriaClinica.presentation.dto.MedicalFileDTO;
import com.example.MiHistoriaClinica.service.MedicalFileService;
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

import java.util.List;


@RestController
@RequestMapping("/medical-file")
@CrossOrigin("*")
public class MedicalFileController {

    private final MedicalFileService medicalFileService;
    private final PatientService patientService;
    private final MedicServiceImpl medicService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);

    @Autowired
    public MedicalFileController(MedicalFileService medicalFileService, PatientService patientService, MedicServiceImpl medicService) {
        this.medicalFileService = medicalFileService;
        this.patientService = patientService;
        this.medicService = medicService;
    }

    @GetMapping("/getAll")
    public List<MedicalFile> getAllMedicalHistory() {
        return medicalFileService.findAll();
    }

    @GetMapping("/get-medical-file")
    public ResponseEntity<MedicalFileDTO> getMedicalHistory(@RequestHeader("Authorization") String token ) throws InvalidTokenException {
        MedicalFileDTO medicalHistory = patientService.getMedicalHistory(jwtValidator.getId(token));
        return new ResponseEntity<>(medicalHistory, HttpStatus.OK);
    }


    @PostMapping("/medic/create")
    public ResponseEntity<MedicalFile> createPatientMedicalHistory(@RequestHeader("Authorization") String token,
                                                                   @RequestHeader("patientLinkCode") String patientLinkCode,
                                                                   @RequestBody MedicalFileDTO medicalHistory)
            throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        MedicalFile createdMedicalFile = medicService.createPatientMedicalHistory(medicId, patientLinkCode, medicalHistory);

        if (createdMedicalFile == null)      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else                                    return new ResponseEntity<>(createdMedicalFile, HttpStatus.CREATED);

    }

    @GetMapping("medic/get")
    public ResponseEntity<MedicalFileDTO> getPatientMedicalHistory(@RequestHeader("patientLinkCode") String patientLinkCode ) throws InvalidTokenException {
        MedicalFileDTO medicalHistory = medicService.getPatientMedicalHistory(patientLinkCode);
        return new ResponseEntity<>(medicalHistory, HttpStatus.OK);
    }

    @PostMapping("medic/update")
    public ResponseEntity<Void> updateMedicalHistory(@RequestHeader("Authorization") String token,
                                                     @RequestHeader("patientLinkCode") String patientLinkCode,
                                                     @RequestBody MedicalFileDTO medicalHistory)
            throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        medicService.createPatientMedicalHistory(medicId, patientLinkCode, medicalHistory);

        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/download-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadMedicalHistory(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        Long id = jwtValidator.getId(token);
        MedicalFile medicalFile = medicalFileService.getMedicalHistoryById(id);

        byte[]  pdfBytes = medicalFileService.parseMedicalHistoryToPDF(medicalFile);

        // Devolver la respuesta con el contenido del PDF y las cabeceras necesarias
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=historia_clinica.pdf")
                .body(pdfBytes);
    }


    @GetMapping("/blood-types")
    public ResponseEntity<List<String>> getBloodTypes() {
        return new ResponseEntity<>(medicalFileService.getBloodTypes(), HttpStatus.OK);
    }
}
