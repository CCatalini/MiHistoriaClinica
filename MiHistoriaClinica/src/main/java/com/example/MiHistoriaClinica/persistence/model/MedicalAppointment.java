package com.example.MiHistoriaClinica.persistence.model;

import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
    private MedicalSpecialtyE specialty;
    private Long matricula;
    private LocalDate appointmentDay;


    @ManyToOne
    @JoinColumn(name = "patientId")
    @JsonIgnore
    private Patient patient;

}
