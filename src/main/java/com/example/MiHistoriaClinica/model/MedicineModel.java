package com.example.MiHistoriaClinica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Medicine")
public class MedicineModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long medicine_id;

    @Column(name="medicine")
    private String medicineName;

    @Column(name="active_ingredient")
    private String activeIngredient;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratorio_id")
    private LabModel lab;



    public Long getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(Long medicine_id) {
        this.medicine_id = medicine_id;
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

    public LabModel getLab() {
        return lab;
    }

    public void setLab(LabModel lab) {
        this.lab = lab;
    }
}
