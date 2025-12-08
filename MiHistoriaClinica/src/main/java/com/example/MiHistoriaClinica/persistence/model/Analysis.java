package com.example.MiHistoriaClinica.persistence.model;

import com.example.MiHistoriaClinica.util.constant.AnalysisE;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @Enumerated(EnumType.STRING)    private AnalysisE name;
    @Enumerated(EnumType.STRING)    private MedicalCenterE medicalCenterE;
    private String description;
    private String status;
    
    // Fecha y hora programada para el estudio
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    
    // ID del turno reservado en study_schedule (para cancelaciones)
    private Long scheduleId;

    @ManyToMany(mappedBy = "analysis", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Patient> patients = new ArrayList<>();

    public void addPatient(Patient patient){
        this.patients.add(patient);
    }
    
    /**
     * Indica si el estudio necesita programar turno
     */
    public boolean needsScheduling() {
        return scheduledDate == null && "Pendiente".equals(status);
    }

}

