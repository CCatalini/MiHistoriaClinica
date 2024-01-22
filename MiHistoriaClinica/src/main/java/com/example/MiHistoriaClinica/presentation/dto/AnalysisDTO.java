package com.example.MiHistoriaClinica.presentation.dto;

import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnalysisDTO {

    private String name;
    private MedicalCenterE medicalCenterE;
    private String description;
    private String status;


    public AnalysisDTO(){

    }

    public AnalysisDTO(String name, MedicalCenterE medicalCenter, String description, String status) {
        this.name = name;
        this.medicalCenterE = MedicalCenterE.valueOf(medicalCenter.name());;
        this.description = description;
        this.status = status;
    }


}
