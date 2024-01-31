package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.persistence.model.*;
import com.example.MiHistoriaClinica.persistence.repository.MedicalFileRepository;
import com.example.MiHistoriaClinica.persistence.repository.PatientRepository;
import com.example.MiHistoriaClinica.service.MedicalHistoryService;
import com.example.MiHistoriaClinica.util.exception.ResourceNotFoundException;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalFileRepository medicalFileRepository;
    private final PatientRepository patientRepository;
    private final MedicalAppointmentServiceImpl medicalAppointmentService;

    public MedicalHistoryServiceImpl(MedicalFileRepository medicalFileRepository, PatientRepository patientRepository, MedicalAppointmentServiceImpl medicalAppointmentService) {
        this.medicalFileRepository = medicalFileRepository;
        this.patientRepository = patientRepository;
        this.medicalAppointmentService = medicalAppointmentService;
    }


    @Override
    public byte[] createPdf(Long id, boolean includeMedicalFile, boolean includeAnalysis, boolean includeMedications, boolean includeAppointments) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Document document = initializePdf(byteArrayOutputStream);
            addContentToPdf(document, id, includeMedicalFile, includeAnalysis, includeMedications, includeAppointments);
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

    private void addContentToPdf(Document document, Long id, boolean includeMedicalFile, boolean includeAnalysis, boolean includeMedications, boolean includeAppointments) {
        addPatientContent(document, id);
        if(noSelectionMade(includeMedicalFile, includeAnalysis, includeMedications, includeAppointments)){
            addAllContent(document, id);
        }else{
            if (includeMedicalFile)     addMedicalFileContent(document, id);
            if (includeMedications)     addMedicinesContent(document, id);
            if (includeAnalysis)        addAnalysisContent(document, id);
            if (includeAppointments)    addAppointmentsContent(document, id);
        }

    }

    private void addAllContent(Document document, Long id) {
        addMedicalFileContent(document, id);
        addMedicinesContent(document, id);
        addAnalysisContent(document, id);
        addAppointmentsContent(document, id);
    }

    private boolean noSelectionMade(boolean includeMedicalFile, boolean includeAnalysis, boolean includeMedications, boolean includeAppointments) {
        return !includeMedicalFile && !includeAnalysis && !includeMedications && !includeAppointments;
    }

    private void addPatientContent(Document document, Long id){
        Patient patient = patientRepository.findById(id).orElseThrow();

        document.add(new Paragraph("Historia Clínica"));
        document.add(new Paragraph("Paciente: " + patient.getName() + " " + patient.getLastname()));
        document.add(new Paragraph("DNI: " + patient.getDni()));
    }

    private void addMedicalFileContent(Document document, Long id) {
        MedicalFile medicalFile = medicalFileRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró el archivo médico con id: " + id)
        );

        document.add(new Paragraph("Peso: " + medicalFile.getWeight() + " kg"));
        document.add(new Paragraph("Altura: " + medicalFile.getHeight() + " cm"));
        document.add(new Paragraph("Tipo de sangre: " + medicalFile.getBloodType()));
        document.add(new Paragraph("Alergias: " + medicalFile.getAllergy()));
        document.add(new Paragraph("Enfermedades crónicas: " + medicalFile.getChronicDisease()));
        document.add(new Paragraph("Medicamentos actuales: " + medicalFile.getActualMedicine()));
        document.add(new Paragraph("Antecedentes familiares: " + medicalFile.getFamilyMedicalHistory()));
    }

    private void addMedicinesContent(Document document, Long id)  {
        List<Medicine> medicineList = patientRepository.getMedicinesByPatientIdAndStatus(id, "Finalizado");

        if (!medicineList.isEmpty()) {
            // Agregar encabezado de la tabla
            Table table = new Table(4); // 4 columnas para Nombre, Dosis, Fecha de Inicio, Fecha de Finalización
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            // Agregar encabezados de columna
            table.addHeaderCell("Nombre");
            table.addHeaderCell("Descripción");
            table.addHeaderCell("Fecha de Prescripción");
            table.addHeaderCell("Comentarios");

            // Agregar filas de datos
            for (Medicine medicine : medicineList) {
                table.addCell(String.valueOf(medicine.getName()));
                table.addCell(medicine.getDescription());
                table.addCell(String.valueOf(medicine.getPrescriptionDay()));
                table.addCell(medicine.getComments());
            }

            // Agregar la tabla al documento
            document.add(new Paragraph("Medicamentos Finalizados").setBold());
            document.add(table);
        }
    }

    private void addAnalysisContent(Document document, Long id){
        List<Analysis> analysisList = patientRepository.getAnalysisByPatientIdAndStatus(id, "Finalizado");

        if(!analysisList.isEmpty()){
            Table table = new Table(3);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addHeaderCell("Análisis");
            table.addHeaderCell("Descripción");
            table.addHeaderCell("Centro Médico");

            for(Analysis analysis : analysisList){
                table.addCell(analysis.getName().getName());
                table.addCell(analysis.getDescription());
                table.addCell(analysis.getMedicalCenterE().getName());
            }

            document.add(new Paragraph("Análisis Finalizados").setBold());
            document.add(table);
        }
    }

    private void addAppointmentsContent(Document document, Long id){
        List<MedicalAppointment> appointments = medicalAppointmentService.getAppointmentListById(id);

        if(!appointments.isEmpty()){
            Table table = new Table(5);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addHeaderCell("Motivo de Consulta");
            table.addHeaderCell("Enfermedad Actual");
            table.addHeaderCell("Examen Físico");
            table.addHeaderCell("Observaciones");
            table.addHeaderCell("Médico");

            for(MedicalAppointment appointment : appointments){
                table.addCell(appointment.getAppointmentReason());
                table.addCell(appointment.getCurrentIllness());
                table.addCell(appointment.getPhysicalExam());
                table.addCell(appointment.getObservations());
                table.addCell(appointment.getMedicFullName());
            }

            document.add(new Paragraph("Citas Médicas").setBold());
            document.add(table);
        }
    }

}
