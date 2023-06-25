package com.example.MiHistoriaClinica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "Analysis")
public class AnalysisModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long analysis_id;

    @Column(nullable = false)
    private String name;

    private String medicalCenter;

    private String description;


    @ManyToMany(mappedBy = "analysis", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<PatientModel> patients = new ArrayList<>();


    public List<PatientModel> getPatients() {
        return patients;
    }

    public void addPatient(PatientModel patientModel){
        this.patients.add(patientModel);
    }
    public void setPatients(List<PatientModel> patients) {
        this.patients = patients;
    }

    public Long getAnalysis_id() {
        return analysis_id;
    }

    public void setAnalysis_id(Long medicalAnalysis_id) {
        this.analysis_id = medicalAnalysis_id;
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

/*
atrinutos:
 nombre
 fecha del turno
 lugar+
 descripcion


 */
