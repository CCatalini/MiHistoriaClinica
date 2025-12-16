package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.persistence.model.*;
import com.example.MiHistoriaClinica.persistence.repository.MedicalFileRepository;
import com.example.MiHistoriaClinica.persistence.repository.PatientRepository;
import com.example.MiHistoriaClinica.service.MedicalHistoryService;
import com.example.MiHistoriaClinica.util.exception.ResourceNotFoundException;
import java.util.Optional;
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
    // Estas variables NO deben compartirse entre distintas generaciones de PDF porque los objetos pueden quedar enlazados a un documento previo.
    // Se inicializan en cada llamada a createPdf() mediante resetStyles().
    private Style titleStyle;
    private Style subtitleStyle;
    private Style columnsStyle;
    private Style bodyStyle;
    private Style contentTableStyle;

    private void resetStyles() throws IOException {
        // Colores corporativos
        DeviceRgb azulCeleste = new DeviceRgb(74, 144, 226); // #4A90E2
        DeviceRgb azulOscuro = new DeviceRgb(41, 128, 185); // #2980B9
        DeviceRgb grisOscuro = new DeviceRgb(52, 73, 94); // #34495E
        
        titleStyle = new Style()
                .setFont(PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD, com.itextpdf.io.font.PdfEncodings.WINANSI, true))
                .setFontSize(22)
                .setBold()
                .setFontColor(azulCeleste)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);

        subtitleStyle = new Style()
                .setFont(PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD, com.itextpdf.io.font.PdfEncodings.WINANSI, true))
                .setFontSize(16)
                .setBold()
                .setFontColor(azulOscuro)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.LEFT);

        columnsStyle = new Style()
                .setFont(PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD, com.itextpdf.io.font.PdfEncodings.WINANSI, true))
                .setFontSize(11)
                .setBold()
                .setFontColor(DeviceRgb.WHITE)
                .setBackgroundColor(azulCeleste)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);

        bodyStyle = new Style()
                .setFont(PdfFontFactory.createFont(FontConstants.HELVETICA, com.itextpdf.io.font.PdfEncodings.WINANSI, true))
                .setFontSize(11)
                .setFontColor(grisOscuro)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.LEFT);

        contentTableStyle = new Style()
                .setFont(PdfFontFactory.createFont(FontConstants.HELVETICA, com.itextpdf.io.font.PdfEncodings.WINANSI, true))
                .setFontSize(10)
                .setFontColor(grisOscuro)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
    }


    public MedicalHistoryServiceImpl(MedicalFileRepository medicalFileRepository, PatientRepository patientRepository, MedicalAppointmentServiceImpl medicalAppointmentService) throws IOException {
        this.medicalFileRepository = medicalFileRepository;
        this.patientRepository = patientRepository;
        this.medicalAppointmentService = medicalAppointmentService;
    }


    @Override
    public byte[] createPdf(Long id, boolean includeMedicalFile, boolean includeAnalysis, boolean includeMedications, boolean includeAppointments, String estadoConsulta, String especialidadMedico) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            resetStyles();
            Document document = initializePdf(byteArrayOutputStream);
            addContentToPdf(document, id, includeMedicalFile, includeAnalysis, includeMedications, includeAppointments, estadoConsulta, especialidadMedico);
            document.close();
            return byteArrayOutputStream.toByteArray(); // Devolver el contenido del PDF
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public byte[] createPdfByLinkCode(String linkCode, boolean includeMedicalFile, boolean includeAnalysis, boolean includeMedications, boolean includeAppointments, String estadoConsulta, String especialidadMedico) {
        Optional<Patient> patientOpt = patientRepository.findByLinkCode(linkCode);
        if (patientOpt.isEmpty()) {
            return new byte[0];
        }
        Long patientId = patientOpt.get().getPatientId();
        return createPdf(patientId, includeMedicalFile, includeAnalysis, includeMedications, includeAppointments, estadoConsulta, especialidadMedico);
    }

    private Document initializePdf(ByteArrayOutputStream byteArrayOutputStream) {
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        return new Document(pdfDocument);
    }

    private void addContentToPdf(Document document, Long id, boolean includeMedicalFile, boolean includeAnalysis, boolean includeMedications, boolean includeAppointments, String estadoConsulta, String especialidadMedico) {
        addPatientContent(document, id);
        if(noSelectionMade(includeMedicalFile, includeAnalysis, includeMedications, includeAppointments)){
            addAllContent(document, id, estadoConsulta, especialidadMedico);
        }else{
            if (includeMedicalFile)     addMedicalFileContent(document, id);
            if (includeMedications)     addMedicinesContent(document, id);
            if (includeAnalysis)        addAnalysisContent(document, id);
            if (includeAppointments)    addAppointmentsContent(document, id, estadoConsulta, especialidadMedico);
        }

    }

    private void addAllContent(Document document, Long id, String estadoConsulta, String especialidadMedico) {
        addMedicalFileContent(document, id);
        addMedicinesContent(document, id);
        addAnalysisContent(document, id);
        addAppointmentsContent(document, id, estadoConsulta, especialidadMedico);
    }

    private boolean noSelectionMade(boolean includeMedicalFile, boolean includeAnalysis, boolean includeMedications, boolean includeAppointments) {
        return !includeMedicalFile && !includeAnalysis && !includeMedications && !includeAppointments;
    }

    private void addPatientContent(Document document, Long id){
        Patient patient = patientRepository.findById(id).orElseThrow();

        // Título principal
        document.add(new Paragraph("MI HISTORIA CLÍNICA").addStyle(titleStyle).setMarginBottom(10));

        // Línea decorativa
        DeviceRgb azulCeleste = new DeviceRgb(74, 144, 226);
        SolidLine lineaAzul = new SolidLine(2f);
        lineaAzul.setColor(azulCeleste);
        document.add(new LineSeparator(lineaAzul).setMarginTop(0).setMarginBottom(15));

        // Tabla de información del paciente
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{1, 3}));
        infoTable.useAllAvailableWidth();
        
        infoTable.addCell(new Paragraph("Paciente:").addStyle(bodyStyle).setBold());
        infoTable.addCell(new Paragraph(patient.getName() + " " + patient.getLastname()).addStyle(bodyStyle));
        
        infoTable.addCell(new Paragraph("DNI:").addStyle(bodyStyle).setBold());
        infoTable.addCell(new Paragraph(String.valueOf(patient.getDni())).addStyle(bodyStyle));
        
        infoTable.addCell(new Paragraph("Email:").addStyle(bodyStyle).setBold());
        infoTable.addCell(new Paragraph(patient.getEmail()).addStyle(bodyStyle));
        
        infoTable.addCell(new Paragraph("Fecha de Nacimiento:").addStyle(bodyStyle).setBold());
        infoTable.addCell(new Paragraph(patient.getBirthdate() != null ? patient.getBirthdate().toString() : "No especificada").addStyle(bodyStyle));
        
        document.add(infoTable);
        document.add(new LineSeparator(new SolidLine()).setMarginTop(15).setMarginBottom(15));
    }

    private void addMedicalFileContent(Document document, Long id) {
        medicalFileRepository.findById(id).ifPresent(medicalFile -> {
            document.add(new LineSeparator(new SolidLine()).setMarginTop(10).setMarginBottom(10));
            document.add(new Paragraph("Ficha Médica").addStyle(subtitleStyle).setMarginBottom(10));

            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
            table.useAllAvailableWidth();
            DeviceRgb grisClaro = new DeviceRgb(240, 240, 240);
            
            table.addCell(new Paragraph("Peso:").addStyle(bodyStyle).setBold().setPadding(5).setBackgroundColor(grisClaro));
            table.addCell(new Paragraph(medicalFile.getWeight() + " kg").addStyle(bodyStyle).setPadding(5));
            
            table.addCell(new Paragraph("Altura:").addStyle(bodyStyle).setBold().setPadding(5).setBackgroundColor(grisClaro));
            table.addCell(new Paragraph(medicalFile.getHeight() + " cm").addStyle(bodyStyle).setPadding(5));
            
            table.addCell(new Paragraph("Tipo de sangre:").addStyle(bodyStyle).setBold().setPadding(5).setBackgroundColor(grisClaro));
            table.addCell(new Paragraph(medicalFile.getBloodType() != null ? medicalFile.getBloodType().name() : "No especificado").addStyle(bodyStyle).setPadding(5));
            
            table.addCell(new Paragraph("Alergias:").addStyle(bodyStyle).setBold().setPadding(5).setBackgroundColor(grisClaro));
            table.addCell(new Paragraph(medicalFile.getAllergy() != null ? medicalFile.getAllergy() : "Ninguna").addStyle(bodyStyle).setPadding(5));
            
            table.addCell(new Paragraph("Enfermedades crónicas:").addStyle(bodyStyle).setBold().setPadding(5).setBackgroundColor(grisClaro));
            table.addCell(new Paragraph(medicalFile.getChronicDisease() != null ? medicalFile.getChronicDisease() : "Ninguna").addStyle(bodyStyle).setPadding(5));
            
            table.addCell(new Paragraph("Medicamentos actuales:").addStyle(bodyStyle).setBold().setPadding(5).setBackgroundColor(grisClaro));
            table.addCell(new Paragraph(medicalFile.getActualMedicine() != null ? medicalFile.getActualMedicine() : "Ninguno").addStyle(bodyStyle).setPadding(5));
            
            table.addCell(new Paragraph("Antecedentes familiares:").addStyle(bodyStyle).setBold().setPadding(5).setBackgroundColor(grisClaro));
            table.addCell(new Paragraph(medicalFile.getFamilyMedicalHistory() != null ? medicalFile.getFamilyMedicalHistory() : "Ninguno").addStyle(bodyStyle).setPadding(5));
            
            document.add(table);
        });
    }

    private void addMedicinesContent(Document document, Long id)  {
        List<Medicine> medicineList = patientRepository.getMedicinesByPatientId(id);

        if (!medicineList.isEmpty()) {
            Table table = new Table(UnitValue.createPercentArray(new float[]{1.5f, 2f, 1.2f, 2f, 1f}));
            table.useAllAvailableWidth();
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addHeaderCell(new Paragraph("Nombre").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Descripción").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Fecha").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Comentarios").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Estado").addStyle(columnsStyle)).setPadding(5);

            DeviceRgb grisClaro = new DeviceRgb(240, 240, 240);
            int rowIndex = 0;
            
            for (Medicine medicine : medicineList) {
                boolean isEvenRow = (rowIndex % 2 == 0);
                
                table.addCell(new Paragraph(String.valueOf(medicine.getName())).addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(medicine.getDescription() != null ? medicine.getDescription() : "-").addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(String.valueOf(medicine.getPrescriptionDay())).addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(medicine.getComments() != null ? medicine.getComments() : "-").addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(medicine.getStatus()).addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                
                rowIndex++;
            }

            document.add(new LineSeparator(new SolidLine()).setMarginTop(10).setMarginBottom(10));
            document.add(new Paragraph("Medicamentos").addStyle(subtitleStyle).setMarginBottom(10));
            document.add(table);
        }
    }

    private void addAnalysisContent(Document document, Long id){
        List<Analysis> analysisList = patientRepository.getAnalysisByPatientId(id);

        if(!analysisList.isEmpty()){
            Table table = new Table(UnitValue.createPercentArray(new float[]{1.5f, 2.5f, 1.5f, 1f}));
            table.useAllAvailableWidth();
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addHeaderCell(new Paragraph("Estudio").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Descripción").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Centro Médico").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Estado").addStyle(columnsStyle)).setPadding(5);

            DeviceRgb grisClaro = new DeviceRgb(240, 240, 240);
            int rowIndex = 0;
            
            for(Analysis analysis : analysisList){
                boolean isEvenRow = (rowIndex % 2 == 0);
                
                String analysisName = (analysis.getName() != null && analysis.getName().getName() != null) 
                    ? analysis.getName().getName() 
                    : "No especificado";
                String medicalCenter = (analysis.getMedicalCenterE() != null && analysis.getMedicalCenterE().getName() != null) 
                    ? analysis.getMedicalCenterE().getName() 
                    : "No especificado";
                
                table.addCell(new Paragraph(analysisName).addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(analysis.getDescription() != null ? analysis.getDescription() : "-").addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(medicalCenter).addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(analysis.getStatus() != null ? analysis.getStatus() : "Pendiente").addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                
                rowIndex++;
            }

            document.add(new LineSeparator(new SolidLine()).setMarginTop(10).setMarginBottom(10));
            document.add(new Paragraph("Estudios Médicos").addStyle(subtitleStyle).setMarginBottom(10));
            document.add(table);
        }
    }

    private void addAppointmentsContent(Document document, Long id, String estadoConsulta, String especialidadMedico){
        List<MedicalAppointment> appointments = medicalAppointmentService.getAppointmentListById(id);
        
        // Filtrar por especialidad si se especificó
        if (especialidadMedico != null && !especialidadMedico.isEmpty()) {
            try {
                com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE especialidadEnum = 
                    com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE.valueOf(especialidadMedico);
                appointments = appointments.stream()
                    .filter(apt -> apt.getSpecialty() == especialidadEnum)
                    .collect(java.util.stream.Collectors.toList());
            } catch (IllegalArgumentException e) {
                // Si la especialidad no es válida, mostrar todas
            }
        }

        if(!appointments.isEmpty()){
            Table table = new Table(UnitValue.createPercentArray(new float[]{1.5f, 1.5f, 1.5f, 2f, 1.2f, 1.2f, 1f}));
            table.useAllAvailableWidth();
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addHeaderCell(new Paragraph("Motivo").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Síntomas").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Examen Físico").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Observaciones").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Médico").addStyle(columnsStyle)).setPadding(5);
            table.addHeaderCell(new Paragraph("Especialidad").addStyle(columnsStyle)).setPadding(5);

            DeviceRgb grisClaro = new DeviceRgb(240, 240, 240);
            int rowIndex = 0;
            
            for(MedicalAppointment appointment : appointments){
                boolean isEvenRow = (rowIndex % 2 == 0);
                
                table.addCell(new Paragraph(appointment.getAppointmentReason() != null ? appointment.getAppointmentReason() : "-").addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(appointment.getCurrentIllness() != null ? appointment.getCurrentIllness() : "-").addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(appointment.getPhysicalExam() != null ? appointment.getPhysicalExam() : "-").addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(appointment.getObservations() != null ? appointment.getObservations() : "-").addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(appointment.getMedicFullName() != null ? appointment.getMedicFullName() : "-").addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));
                table.addCell(new Paragraph(appointment.getSpecialty() != null ? appointment.getSpecialty().name() : "-").addStyle(contentTableStyle).setPadding(5).setBackgroundColor(isEvenRow ? grisClaro : DeviceRgb.WHITE));

                rowIndex++;
            }

            document.add(new LineSeparator(new SolidLine()).setMarginTop(10).setMarginBottom(10));
            document.add(new Paragraph("Consultas Médicas").addStyle(subtitleStyle).setMarginBottom(10));
            document.add(table);
        }
    }

}
