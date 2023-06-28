package com.example.MiHistoriaClinica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "turno")
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

    @ManyToOne
    @JoinColumn(name = "patientId")
    @JsonIgnore
    private PatientModel patient;


    
    public LocalTime getHoraTurno() {
        return horaTurno;
    }

    public void setHoraTurno(LocalTime horaTurno) {
        this.horaTurno = horaTurno;
    }

    public String getMedicFullName() {
        return medicFullName;
    }

    public void setMedicFullName(String medicFullName) {
        this.medicFullName = medicFullName;
    }

    public LocalDate getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(LocalDate fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public PatientModel getPatient() {
        return patient;
    }

    public void setPatient(PatientModel patient) {
        this.patient = patient;
    }

    public void setTurnoId(Long turnoId) {
        this.turnoId = turnoId;
    }

    public Long getTurnoId() {
        return turnoId;
    }
}
