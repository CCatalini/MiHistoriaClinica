package com.example.MiHistoriaClinica.presentation.dto;

import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class AnalysisDTO {

    private String name;
    private String medicalCenterE;
    private String description;
    private String status;
    private LocalDate scheduledDate;


    public AnalysisDTO(){

    }

    public AnalysisDTO(String name, String medicalCenter, String description, String status, LocalDate scheduledDate) {
        this.name = name;
        this.medicalCenterE = medicalCenter;
        this.description = description;
        this.status = status;
        this.scheduledDate = scheduledDate;
    }


}
