package com.example.MiHistoriaClinica.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long patientId;
    private String name;
    private String lastname;
    @Column(nullable = false, unique = true)
    private Long dni;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private Date birthdate;
    @Column(name = "link_code")
    private String linkCode;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_medic",
            joinColumns = @JoinColumn(name = "patientId"),
            inverseJoinColumns = @JoinColumn(name = "medicId")
    )
    @JsonManagedReference
    @JsonIgnoreProperties("patients")
    private List<Medic> medics = new ArrayList<>();


    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private MedicalFile medicalFile;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_medicine",
            joinColumns = @JoinColumn(name = "patientId"),
            inverseJoinColumns = @JoinColumn(name = "medicineId")
    )
    @JsonManagedReference
    private List<Medicine> medicines = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_analysis",
            joinColumns = @JoinColumn(name = "patientId"),
            inverseJoinColumns = @JoinColumn(name = "analysisId")
    )
    @JsonManagedReference
    private List<Analysis> analysis = new ArrayList<>();

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<MedicalAppointment> medicalAppointments;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Turnos> turnos;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        return Objects.equals(patientId, patient.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId);
    }

}