package com.example.MiHistoriaClinica.presentation.dto;

import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicTurnosDTO {
    private Long medicId;
    private String medicFullName;
    private MedicalSpecialtyE specialty;
    private String medicalCenter;
    private List<TurnoDisponibleDTO> availableTurnos;
} 