package com.example.MiHistoriaClinica.dto;

public class MedicalHistoryModelDTO {

    private String weight;
    private String height;
    private String allergy;
    private String bloodType;
    private String chronicDisease;
    private String actualMedicine;
    private String familyMedicalHistory;

    public MedicalHistoryModelDTO(String weight, String height, String allergy, String bloodType, String chronicDisease, String actualMedicine, String familyMedicalHistory) {
        this.weight = weight;
        this.height = height;
        this.allergy = allergy;
        this.bloodType = bloodType;
        this.chronicDisease = chronicDisease;
        this.actualMedicine = actualMedicine;
        this.familyMedicalHistory = familyMedicalHistory;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getChronicDisease() {
        return chronicDisease;
    }

    public void setChronicDisease(String chronicDisease) {
        this.chronicDisease = chronicDisease;
    }

    public String getActualMedicine() {
        return actualMedicine;
    }

    public void setActualMedicine(String actualMedicine) {
        this.actualMedicine = actualMedicine;
    }

    public String getFamilyMedicalHistory() {
        return familyMedicalHistory;
    }

    public void setFamilyMedicalHistory(String familyMedicalHistory) {
        this.familyMedicalHistory = familyMedicalHistory;
    }
}
