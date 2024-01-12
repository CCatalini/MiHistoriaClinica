package com.example.MiHistoriaClinica.presentation.dto;

import jakarta.persistence.Column;

public class MedicineDTO {

    private String medicineName;
    private String activeIngredient;
    private String lab;
    private String description;
    private String status;


    public MedicineDTO(String medicineName, String activeIngredient, String lab, String description, String status) {
        this.medicineName = medicineName;
        this.activeIngredient = activeIngredient;
        this.lab = lab;
        this.description = description;
        this.status = status;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
