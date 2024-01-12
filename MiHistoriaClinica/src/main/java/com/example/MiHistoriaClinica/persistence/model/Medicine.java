package com.example.MiHistoriaClinica.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Medicine")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long medicineId;

    @Column(name="medicine")
    private String medicineName;
    @Column(name="active_ingredient")
    private String activeIngredient;
    private String lab;
    private String description;
    private String status;


    @ManyToMany(mappedBy = "medicines")
    @JsonBackReference
    private List<Patient> patients = new ArrayList<>();



    public void addPatient(Patient patient){
        patients.add(patient);
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicine_id) {
        this.medicineId = medicine_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
