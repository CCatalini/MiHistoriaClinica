package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.persistence.model.MedicalHistory;
import com.example.MiHistoriaClinica.persistence.repository.MedicalHistoryRepository;
import com.example.MiHistoriaClinica.service.MedicalHistoryService;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    public MedicalHistoryServiceImpl(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }


    @Override
    public MedicalHistory getMedicalHistoryById(Long id) {
        return medicalHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la historia clínica con id: " + id + "."));
    }

    @Override
    public List<MedicalHistory> findAll() {
        return medicalHistoryRepository.findAll();
    }

    @Override
    public byte[] parseMedicalHistoryToPDF(MedicalHistory historiaClinica) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            Document document = initializePdf(byteArrayOutputStream);
            addMedicalHistoryContent(document, historiaClinica);
            document.close();

            return byteArrayOutputStream.toByteArray(); // Devolver el contenido del PDF
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


    private Document initializePdf(ByteArrayOutputStream byteArrayOutputStream) {
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        return new Document(pdfDocument);
    }

    private void addMedicalHistoryContent(Document document, MedicalHistory medicalHistory) {
        document.add(new Paragraph("Historia Clínica"));
        document.add(new Paragraph("Paciente: " + medicalHistory.getPatient().getName() + " " + medicalHistory.getPatient().getLastname()));
        document.add(new Paragraph("DNI: " + medicalHistory.getPatient().getDni()));
    }


}
