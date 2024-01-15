package com.example.MiHistoriaClinica.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MedicalAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long medicalAppointmentId;

    private String appointmentReason;
    private String currentIllness;
    private String physicalExam;
    private String observations;
    private String medicFullName;
    private String specialty;
    private Long matricula;

    @ManyToOne
    @JoinColumn(name = "patientId")
    @JsonIgnore
    private Patient patient;

}
