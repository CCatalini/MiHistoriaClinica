package com.example.MiHistoriaClinica.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "Analysis")
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long analysis_id;

    @Column(nullable = false)
    private String name;
    private String medicalCenter;
    private String description;
    private String status;


    @ManyToMany(mappedBy = "analysis", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Patient> patients = new ArrayList<>();


    public List<Patient> getPatients() {
        return patients;
    }

    public void addPatient(Patient patient){
        this.patients.add(patient);
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

