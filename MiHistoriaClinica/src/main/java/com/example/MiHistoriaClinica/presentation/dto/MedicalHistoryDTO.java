package com.example.MiHistoriaClinica.presentation.dto;

import com.example.MiHistoriaClinica.persistence.model.Analysis;
import com.example.MiHistoriaClinica.persistence.model.MedicalAppointment;
import com.example.MiHistoriaClinica.persistence.model.MedicalFile;
import com.example.MiHistoriaClinica.persistence.model.Medicine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class MedicalHistoryDTO {

    private MedicalFileDTO medicalFile;
    private MedicineDTO medicine;
    private AnalysisDTO analysis;
    private MedicalAppointmentDTO medicalAppointment;
    private String medicName;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate dateTo;

    // TODO add more fields: CENTRO MEDICO - NOMBRE DE MEDICAMENTO - NOMBRE DE ANALYSIS -
}
