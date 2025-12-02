package com.example.MiHistoriaClinica.persistence.model;

import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Turnos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long turnoId;
    @Column(name = "fecha_turno")
    private LocalDate fechaTurno;
    @Column(name = "hora_turno")
    private LocalTime horaTurno;

    private String medicFullName;
    private MedicalSpecialtyE medicSpecialty;
    @Enumerated(EnumType.STRING)
    private MedicalCenterE medicalCenter;

    @ManyToOne
    @JoinColumn(name = "patientId")
    @JsonIgnore
    private Patient patient;

    // Campos denormalizados para información del paciente (para evitar problemas de serialización)
    private String patientName;
    private String patientLastname;
    private Long patientDni;
    private String patientEmail;

    @ManyToOne
    @JoinColumn(name = "medicId")
    @JsonIgnore
    private Medic medic;

    private boolean available = true;

}
