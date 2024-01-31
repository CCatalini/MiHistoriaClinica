package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.persistence.model.*;
import com.example.MiHistoriaClinica.persistence.repository.MedicalFileRepository;
import com.example.MiHistoriaClinica.persistence.repository.PatientRepository;
import com.example.MiHistoriaClinica.service.MedicalHistoryService;
import com.example.MiHistoriaClinica.util.exception.ResourceNotFoundException;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalFileRepository medicalFileRepository;
    private final PatientRepository patientRepository;
    private final MedicalAppointmentServiceImpl medicalAppointmentService;
    Style titleStyle = new Style()
            .setFont(PdfFontFactory.createFont(FontConstants.TIMES_ROMAN, "UTF-8", true))
            .setFontSize(20)
            .setBold()
            .setFontColor(DeviceRgb.BLACK);
    Style subtitleStyle = new Style()
            .setFont(PdfFontFactory.createFont(FontConstants.TIMES_ROMAN, "UTF-8", true))
            .setFontSize(16)
            .setBold()
            .setFontColor(DeviceRgb.BLACK);
    Style columnsStyle = new Style()
            .setFont(PdfFontFactory.createFont(FontConstants.TIMES_ROMAN, "UTF-8", true))
            .setFontSize(12)
            .setBold()
            .setFontColor(DeviceRgb.BLACK);
    Style bodyStyle = new Style()
            .setFont(PdfFontFactory.createFont(FontConstants.TIMES_ROMAN, "UTF-8", true))
            .setFontSize(12)
            .setFontColor(DeviceRgb.BLACK);
    Style contentTableStyle = new Style()
            .setFont(PdfFontFactory.createFont(FontConstants.TIMES_ROMAN, "UTF-8", true))
            .setFontSize(10)
            .setFontColor(DeviceRgb.BLACK);


    public MedicalHistoryServiceImpl(MedicalFileRepository medicalFileRepository, PatientRepository patientRepository, MedicalAppointmentServiceImpl medicalAppointmentService) throws IOException {
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
        pdfDocument.setDefaultPageSize(PageSize.A4);
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

        document.add(new Paragraph("Mi Historia Clinica").addStyle(titleStyle));
        document.add(new Paragraph("Paciente: " + patient.getName() + " " + patient.getLastname()).addStyle(subtitleStyle));
        document.add(new Paragraph("Dni: " + patient.getDni()).addStyle(subtitleStyle));
    }

    private void addMedicalFileContent(Document document, Long id) {
        MedicalFile medicalFile = medicalFileRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró el archivo médico con id: " + id)
        );

        document.add(new LineSeparator(new SolidLine()).setMarginTop(5).setMarginBottom(5));
        document.add(new Paragraph("Archivo Medico").addStyle(subtitleStyle));

        document.add(new Paragraph("Peso: " + medicalFile.getWeight() + " kg").addStyle(bodyStyle));
        document.add(new Paragraph("Altura: " + medicalFile.getHeight() + " cm").addStyle(bodyStyle));
        document.add(new Paragraph("Tipo de sangre: " + medicalFile.getBloodType()).addStyle(bodyStyle));
        document.add(new Paragraph("Alergias: " + medicalFile.getAllergy()).addStyle(bodyStyle));
        document.add(new Paragraph("Enfermedades cronicas: " + medicalFile.getChronicDisease()).addStyle(bodyStyle));
        document.add(new Paragraph("Medicamentos actuales: " + medicalFile.getActualMedicine()).addStyle(bodyStyle));
        document.add(new Paragraph("Antecedentes familiares: " + medicalFile.getFamilyMedicalHistory()).addStyle(bodyStyle));
    }

    private void addMedicinesContent(Document document, Long id)  {
        List<Medicine> medicineList = patientRepository.getMedicinesByPatientId(id);

        if (!medicineList.isEmpty()) {
            Table table = new Table(5);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addHeaderCell("Nombre").addStyle(columnsStyle);
            table.addHeaderCell("Descripción").addStyle(columnsStyle);
            table.addHeaderCell("Fecha de Prescripción").addStyle(columnsStyle);
            table.addHeaderCell("Comentarios").addStyle(columnsStyle);
            table.addHeaderCell("Estado").addStyle(columnsStyle);

            for (Medicine medicine : medicineList) {
                table.addCell(String.valueOf(medicine.getName())).addStyle(contentTableStyle);
                table.addCell(medicine.getDescription()).addStyle(contentTableStyle);
                table.addCell(String.valueOf(medicine.getPrescriptionDay())).addStyle(contentTableStyle);
                table.addCell(medicine.getComments()).addStyle(contentTableStyle);
                table.addCell(medicine.getStatus()).addStyle(contentTableStyle);
            }

            document.add(new LineSeparator(new SolidLine()).setMarginTop(5).setMarginBottom(5));
            document.add(new Paragraph("Medicamentos").addStyle(subtitleStyle));
            document.add(table);
        }
    }

    private void addAnalysisContent(Document document, Long id){
        List<Analysis> analysisList = patientRepository.getAnalysisByPatientId(id);

        if(!analysisList.isEmpty()){
            Table table = new Table(4);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addHeaderCell("Analisis").addStyle(columnsStyle);
            table.addHeaderCell("Descripcion").addStyle(columnsStyle);
            table.addHeaderCell("Centro Medico").addStyle(columnsStyle);
            table.addHeaderCell("Estado").addStyle(columnsStyle);

            for(Analysis analysis : analysisList){
                table.addCell(analysis.getName().getName()).addStyle(contentTableStyle);
                table.addCell(analysis.getDescription()).addStyle(contentTableStyle);
                table.addCell(analysis.getMedicalCenterE().getName()).addStyle(contentTableStyle);
                table.addCell(analysis.getStatus()).addStyle(contentTableStyle);
            }

            document.add(new LineSeparator(new SolidLine()).setMarginTop(5).setMarginBottom(5));
            document.add(new Paragraph("Analisis").addStyle(subtitleStyle));
            document.add(table);
        }
    }

    private void addAppointmentsContent(Document document, Long id){
        List<MedicalAppointment> appointments = medicalAppointmentService.getAppointmentListById(id);

        if(!appointments.isEmpty()){
            Table table = new Table(5);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addHeaderCell("Motivo de Consulta").addStyle(columnsStyle);
            table.addHeaderCell("Enfermedad Actual").addStyle(columnsStyle);
            table.addHeaderCell("Examen Fisico").addStyle(columnsStyle);
            table.addHeaderCell("Observaciones").addStyle(columnsStyle);
            table.addHeaderCell("Medico").addStyle(columnsStyle);

            for(MedicalAppointment appointment : appointments){
                table.addCell(appointment.getAppointmentReason()).addStyle(contentTableStyle);
                table.addCell(appointment.getCurrentIllness()).addStyle(contentTableStyle);
                table.addCell(appointment.getPhysicalExam()).addStyle(contentTableStyle);
                table.addCell(appointment.getObservations()).addStyle(contentTableStyle);
                table.addCell(appointment.getMedicFullName()).addStyle(contentTableStyle);
            }

            document.add(new LineSeparator(new SolidLine()).setMarginTop(5).setMarginBottom(5));
            document.add(new Paragraph("Consultas Medicas").addStyle(subtitleStyle));
            document.add(table);
        }
    }

}
