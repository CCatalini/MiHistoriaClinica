package com.example.MiHistoriaClinica.persistence.model;

import jakarta.persistence.*;

@Entity
@Table(name="medicalHistory")
public class MedicalHistoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long history_id;

    private String weight;
    private String height;
    private String allergy;
    private String bloodType;
    private String chronicDisease;
    private String actualMedicine;

    private String familyMedicalHistory;


    @OneToOne
    @JoinColumn(name = "patientId")
    private PatientModel patient;


    public PatientModel getPatient() {
        return patient;
    }

    public void setPatient(PatientModel patient) {
        this.patient = patient;
    }



    public Long getHistory_id() {
        return history_id;
    }

    public void setHistory_id(Long medicalHistory_id) {
        this.history_id = medicalHistory_id;
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
