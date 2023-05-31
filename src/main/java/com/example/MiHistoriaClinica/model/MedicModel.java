package com.example.MiHistoriaClinica.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Medic")
public class MedicModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long medic_id;

    private String name;

    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private Long dni;

    @Column (nullable = false, unique= true)
    private Long matricula;

    private String specialty;

    private String password;



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_medic",
            joinColumns = @JoinColumn(name = "medic_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private List<PatientModel> patients = new ArrayList<>();

    public List<PatientModel> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientModel> patients) {
        this.patients = patients;
    }




    public Long getMedic_id() {
        return medic_id;
    }

    public void setMedic_id(Long doctor_id) {
        this.medic_id = doctor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
