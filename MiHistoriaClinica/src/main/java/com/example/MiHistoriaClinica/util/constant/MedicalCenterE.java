package com.example.MiHistoriaClinica.util.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum MedicalCenterE {
    SEDE_PRINCIPAL_HOSPITAL_AUSTRAL("Sede Principal - Hospital Universitario Austral"),
    CENTRO_ESPECIALIDAD_OFFICIA("Centro de especialidad Officia"),
    CENTRO_ESPECIALIDAD_CHAMPAGNAT("Centro de especialidad Champagnat"),
    CENTRO_ESPECIALIDAD_LUJAN("Centro de especialidad Lujan");

    private final String name;

    MedicalCenterE(String name) {
        this.name = name;
    }

    public static List<String> getAllMedicalCenters() {
        return Arrays.stream(MedicalCenterE.values())
                .map(MedicalCenterE::getName)
                .collect(Collectors.toList());
    }

    public static MedicalCenterE getEnumFromName(String medicalCenter) {
        // Primero intentar buscar por nombre del enum (SEDE_PRINCIPAL_HOSPITAL_AUSTRAL)
        try {
            return MedicalCenterE.valueOf(medicalCenter);
        } catch (IllegalArgumentException e) {
            // Si no es un enum válido, buscar por nombre legible
            for (MedicalCenterE medicalCenterE : MedicalCenterE.values()) {
                if (medicalCenterE.getName().equalsIgnoreCase(medicalCenter)) {
                    return medicalCenterE;
                }
            }
            return null;
        }
    }
}
