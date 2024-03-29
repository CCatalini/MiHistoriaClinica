package com.example.MiHistoriaClinica.persistence.model;

import com.example.MiHistoriaClinica.util.constant.BloodTypeE;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MedicalFile implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long history_id;

    private String weight;
    private String height;
    private String allergy;
    private BloodTypeE bloodType;
    private String chronicDisease;
    private String actualMedicine;
    private String familyMedicalHistory;

    @OneToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

}
