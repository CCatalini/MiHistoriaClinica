package com.example.MiHistoriaClinica.presentation.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
