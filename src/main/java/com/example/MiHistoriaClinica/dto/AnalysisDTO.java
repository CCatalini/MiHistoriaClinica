package com.example.MiHistoriaClinica.dto;

public class AnalysisDTO {

    private String name;
    private String medicalCenter;
    private String description;

    public AnalysisDTO(){

    }

    public AnalysisDTO(String name, String medicalCenter, String description) {
        this.name = name;
        this.medicalCenter = medicalCenter;
        this.description = description;
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
