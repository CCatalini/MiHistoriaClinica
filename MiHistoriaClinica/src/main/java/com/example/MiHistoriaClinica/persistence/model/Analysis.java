package com.example.MiHistoriaClinica.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    public void addPatient(Patient patient){
        this.patients.add(patient);
    }

}

