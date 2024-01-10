package com.example.MiHistoriaClinica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "MedicalAppointment")
public class MedicalAppointmentModel {

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


    // todo agregar specialty

    @ManyToOne
    @JoinColumn(name = "patientId")
    @JsonIgnore
    private PatientModel patient;

    public PatientModel getPatient() {
        return patient;
    }

    public void setPatient(PatientModel patient) {
        this.patient = patient;
    }

    public void setMedicalAppointmentId(Long medicalAppointmentId) {
        this.medicalAppointmentId = medicalAppointmentId;
    }

    public Long getMedicalAppointmentId() {
        return medicalAppointmentId;
    }

    public String getAppointmentReason() {
        return appointmentReason;
    }

    public void setAppointmentReason(String appointmentReason) {
        this.appointmentReason = appointmentReason;
    }

    public String getCurrentIllness() {
        return currentIllness;
    }

    public void setCurrentIllness(String currentIllness) {
        this.currentIllness = currentIllness;
    }

    public String getPhysicalExam() {
        return physicalExam;
    }

    public void setPhysicalExam(String physicalExam) {
        this.physicalExam = physicalExam;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getMedicFullName() {
        return medicFullName;
    }

    public void setMedicFullName(String medicFullName) {
        this.medicFullName = medicFullName;
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



}
