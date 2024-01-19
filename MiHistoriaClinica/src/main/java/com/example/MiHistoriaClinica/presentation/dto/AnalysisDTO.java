package com.example.MiHistoriaClinica.presentation.dto;

import com.example.MiHistoriaClinica.util.constant.AnalysisE;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnalysisDTO {

    private AnalysisE name;
    private MedicalCenterE medicalCenterE;
    private String description;
    private String status;


    public AnalysisDTO(){

    }

    public AnalysisDTO(AnalysisE name, MedicalCenterE medicalCenter, String description, String status) {
        this.name = name;
        this.medicalCenterE = medicalCenter;
        this.description = description;
        this.status = status;
    }


}
