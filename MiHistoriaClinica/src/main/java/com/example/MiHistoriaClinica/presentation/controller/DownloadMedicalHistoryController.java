package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.persistence.model.MedicalHistory;
import com.example.MiHistoriaClinica.service.MedicalHistoryService;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/download-medical-history")
public class DownloadMedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);

    @Autowired
    public DownloadMedicalHistoryController(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    @GetMapping(value = "/download-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadMedicalHistory(@RequestHeader("Authorization") String token) throws InvalidTokenException {

        Long id = jwtValidator.getId(token);
        MedicalHistory medicalHistory = medicalHistoryService.getMedicalHistoryById(id);

        byte[] pdfBytes = convertirHistoriaClinicaAPDF(medicalHistory);

        // Devolver la respuesta con el contenido del PDF y las cabeceras necesarias
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=historia_clinica.pdf")
                .body(pdfBytes);
    }

    private byte[] convertirHistoriaClinicaAPDF(MedicalHistory historiaClinica) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // Inicializar el documento PDF
            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            // Agregar contenido al PDF (adaptar según tus atributos de HistoriaClinica)
            document.add(new Paragraph("Historia Clínica"));
            document.add(new Paragraph("Paciente: " + historiaClinica.getPatient().getName() + " " + historiaClinica.getPatient().getLastname()));
            document.add(new Paragraph("DNI: " + historiaClinica.getPatient().getDni()));

            // Cerrar el documento
            document.close();

            // Devolver los bytes del documento PDF
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();  // Manejar errores adecuadamente en tu aplicación
            return new byte[0];
        }
    }
}

