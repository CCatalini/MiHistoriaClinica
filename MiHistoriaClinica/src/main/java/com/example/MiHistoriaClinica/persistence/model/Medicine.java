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

}
