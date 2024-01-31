package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.util.constant.BloodTypeE;
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
    public MedicalFile getMedicalFileById(Long id) {
        return medicalFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la historia clínica con id: " + id + "."));
    }

    @Override
    public List<MedicalFile> findAll() {
        return medicalFileRepository.findAll();
    }

    @Override
    public List<String> getBloodTypes() {
        return BloodTypeE.getTypes();
    }






}
