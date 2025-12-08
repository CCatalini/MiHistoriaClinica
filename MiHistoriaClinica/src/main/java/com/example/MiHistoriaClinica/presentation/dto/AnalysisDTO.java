package com.example.MiHistoriaClinica.presentation.dto;

import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class AnalysisDTO {

    private String name;
    private String medicalCenterE;
    private String description;
    private String status;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;


    public AnalysisDTO(){

    }

    public AnalysisDTO(String name, String medicalCenter, String description, String status, LocalDate scheduledDate, LocalTime scheduledTime) {
        this.name = name;
        this.medicalCenterE = medicalCenter;
        this.description = description;
        this.status = status;
        this.scheduledDate = scheduledDate;
        this.scheduledTime = scheduledTime;
    }


}
