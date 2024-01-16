package com.example.MiHistoriaClinica.presentation.dto;

import com.example.MiHistoriaClinica.persistence.model.MedicalFile;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicalFileDTO {

    private String weight;
    private String height;
    private String allergy;
    private String bloodType;
    private String chronicDisease;
    private String actualMedicine;
    private String familyMedicalHistory;

    public MedicalFileDTO(MedicalFile medicalFile) {
        this.weight = medicalFile.getWeight();
        this.height = medicalFile.getHeight();
        this.allergy = medicalFile.getAllergy();
        this.bloodType = medicalFile.getBloodType();
        this.chronicDisease = medicalFile.getChronicDisease();
        this.actualMedicine = medicalFile.getActualMedicine();
        this.familyMedicalHistory = medicalFile.getFamilyMedicalHistory();
    }

}
