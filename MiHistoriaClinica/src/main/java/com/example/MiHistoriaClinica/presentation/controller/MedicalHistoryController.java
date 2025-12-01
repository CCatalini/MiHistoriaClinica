package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.service.implementation.MedicalHistoryServiceImpl;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medical-history")
@CrossOrigin("*")
public class MedicalHistoryController {

    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);
    private final MedicalHistoryServiceImpl medicalHistoryService;

    public MedicalHistoryController(MedicalHistoryServiceImpl medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;

    }

    @GetMapping(value = "/download-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadMedicalHistory(@RequestHeader("Authorization") String token,
                                                         @RequestParam(required = false) boolean includeMedicalFile,
                                                         @RequestParam(required = false) boolean includeAnalysis,
                                                         @RequestParam(required = false) boolean includeMedications,
                                                         @RequestParam(required = false) boolean includeAppointments,
                                                         @RequestParam(required = false) String estadoConsulta,
                                                         @RequestParam(required = false) String especialidadMedico)
                                                        throws InvalidTokenException {
        Long id = jwtValidator.getId(token);
        byte[] pdf = medicalHistoryService.createPdf(id, includeMedicalFile, includeAnalysis, includeMedications, includeAppointments, estadoConsulta, especialidadMedico);

        // Devolver la respuesta con el contenido del PDF y las cabeceras necesarias
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=historia_clinica.pdf")
                .body(pdf);
    }

    @GetMapping(value = "/download-pdf-by-linkcode", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadMedicalHistoryByLinkCode(@RequestHeader("patientLinkCode") String linkCode,
                                                                    @RequestParam(required = false) boolean includeMedicalFile,
                                                                    @RequestParam(required = false) boolean includeAnalysis,
                                                                    @RequestParam(required = false) boolean includeMedications,
                                                                    @RequestParam(required = false) boolean includeAppointments,
                                                                    @RequestParam(required = false) String estadoConsulta,
                                                                    @RequestParam(required = false) String especialidadMedico) {
        byte[] pdf = medicalHistoryService.createPdfByLinkCode(linkCode, includeMedicalFile, includeAnalysis, includeMedications, includeAppointments, estadoConsulta, especialidadMedico);

        // Devolver la respuesta con el contenido del PDF y las cabeceras necesarias
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=historia_clinica.pdf")
                .body(pdf);
    }
}
