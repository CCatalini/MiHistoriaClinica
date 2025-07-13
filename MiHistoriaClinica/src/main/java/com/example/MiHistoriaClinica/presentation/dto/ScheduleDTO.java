package com.example.MiHistoriaClinica.presentation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;

@Getter
@Setter
public class ScheduleDTO {
    private LocalDate startDate; // fecha desde la que se generarán los turnos
    private LocalDate endDate;   // fecha hasta la que se generarán los turnos (inclusive)
    private List<DayOfWeek> daysOfWeek; // días seleccionados
    private LocalTime startTime; // hora de inicio de la jornada
    private LocalTime endTime;   // hora de fin de la jornada
    private int durationMinutes; // duración de cada turno en minutos
    private MedicalCenterE medicalCenter;
} 