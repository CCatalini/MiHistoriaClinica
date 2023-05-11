package com.example.MiHistoriaClinica.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long role_id;

    private String name;


    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<MedicModel> medics = new ArrayList<>();

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<PatientModel> patients = new ArrayList<>();


    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public List<MedicModel> getMedics() {
        return medics;
    }

    public void setMedics(List<MedicModel> medics) {
        this.medics = medics;
    }

    public List<PatientModel> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientModel> patients) {
        this.patients = patients;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
