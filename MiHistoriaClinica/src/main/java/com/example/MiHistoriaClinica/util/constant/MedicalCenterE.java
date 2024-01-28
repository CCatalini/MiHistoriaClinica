package com.example.MiHistoriaClinica.util.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum MedicalCenterE {
    HOSPITAL_AUSTRAL("Hospital Austral"),
    CONSULTORIOS_ESCOBAR("Consultorios Escobar"),
    CONSULTORIOS_CHAMPAGNAT("Consultorios Champagnat"),
    HOSPITAL_ALEMAN("Hospital Aleman"),
    HOSPITAL_ITALIANO("Hospital Italiano"),
    HOSPITAL_GARRAHAN("Hospital Garrahan"),
    HOSPITAL_POSADAS("Hospital Posadas");

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
        for (MedicalCenterE medicalCenterE : MedicalCenterE.values()) {
            if (medicalCenterE.getName().equalsIgnoreCase(medicalCenter)) {
                return medicalCenterE;
            }
        }
        return null;
    }
}
