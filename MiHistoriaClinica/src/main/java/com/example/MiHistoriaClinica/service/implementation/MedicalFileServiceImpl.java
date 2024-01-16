package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.util.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.persistence.model.MedicalFile;
import com.example.MiHistoriaClinica.persistence.repository.MedicalFileRepository;
import com.example.MiHistoriaClinica.service.MedicalFileService;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalFileServiceImpl implements MedicalFileService {

    private final MedicalFileRepository medicalFileRepository;

    @Autowired
    public MedicalFileServiceImpl(MedicalFileRepository medicalFileRepository) {
        this.medicalFileRepository = medicalFileRepository;
    }


    @Override
    public MedicalFile getMedicalHistoryById(Long id) {
        return medicalFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la historia clínica con id: " + id + "."));
    }

    @Override
    public List<MedicalFile> findAll() {
        return medicalFileRepository.findAll();
    }

    @Override
    public byte[] parseMedicalHistoryToPDF(MedicalFile historiaClinica) {
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

    private void addMedicalHistoryContent(Document document, MedicalFile medicalFile) {
        document.add(new Paragraph("Historia Clínica"));
        document.add(new Paragraph("Paciente: " + medicalFile.getPatient().getName() + " " + medicalFile.getPatient().getLastname()));
        document.add(new Paragraph("DNI: " + medicalFile.getPatient().getDni()));
    }


}
