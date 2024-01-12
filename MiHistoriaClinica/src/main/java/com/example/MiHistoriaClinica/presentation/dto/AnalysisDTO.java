package com.example.MiHistoriaClinica.presentation.dto;

public class AnalysisDTO {

    private String name;
    private String medicalCenter;
    private String description;
    private String status;

    public AnalysisDTO(){

    }

    public AnalysisDTO(String name, String medicalCenter, String description, String status) {
        this.name = name;
        this.medicalCenter = medicalCenter;
        this.description = description;
        this.status = status;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedicalCenter() {
        return medicalCenter;
    }

    public void setMedicalCenter(String medicalCenter) {
        this.medicalCenter = medicalCenter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
