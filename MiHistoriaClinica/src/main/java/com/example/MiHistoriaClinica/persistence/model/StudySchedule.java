package com.example.MiHistoriaClinica.persistence.model;

import com.example.MiHistoriaClinica.util.constant.AnalysisE;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa un turno disponible para realizar un estudio médico.
 * Los turnos se precargan desde un archivo SQL.
 */
@Entity
@Getter
@Setter
@Table(name = "study_schedule")
public class StudySchedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnalysisE analysisType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedicalCenterE medicalCenter;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false)
    private LocalTime hora;
    
    // true = disponible, false = reservado
    @Column(nullable = false)
    private boolean available = true;
    
    // ID del paciente que reservó (null si está disponible)
    private Long patientId;
    
    // ID del análisis asociado (null si está disponible)
    private Long analysisId;
}

