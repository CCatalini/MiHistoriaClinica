package com.example.MiHistoriaClinica.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientQueueDTO {
    private Long turnoId;
    private String patientFullName;
    private LocalDate date;
    private LocalTime time;
} 