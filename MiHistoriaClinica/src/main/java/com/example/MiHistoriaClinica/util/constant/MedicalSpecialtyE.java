package com.example.MiHistoriaClinica.util.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum MedicalSpecialtyE {
    CARDIOLOGIA("Cardiología"),
    DERMATOLOGIA("Dermatología"),
    ENDOCRINOLOGIA("Endocrinología"),
    GASTROENTEROLOGIA("Gastroenterología"),
    HEMATOLOGIA("Hematología"),
    INFECTOLOGIA("Infectología"),
    NEUROLOGIA("Neurología"),
    ONCOLOGIA("Oncología"),
    OFTALMOLOGIA("Oftalmología"),
    OTORRINOLARINGOLOGIA("Otorrinolaringología"),
    PEDIATRIA("Pediatría"),
    PSIQUIATRIA("Psiquiatría"),
    RADIOLOGIA("Radiología"),
    REUMATOLOGIA("Reumatología"),
    TRAUMATOLOGIA("Traumatología"),
    UROLOGIA("Urología"),
    GINECOLOGIA("Ginecología"),
    MEDICINA_INTERNA("Medicina Interna"),
    CIRUGIA_GENERAL("Cirugía General"),
    ANESTESIOLOGIA("Anestesiología");

    private final String name;

    MedicalSpecialtyE(String name) {
        this.name = name;
    }


    public static MedicalSpecialtyE getEnumFromName(String specialty) {
        for (MedicalSpecialtyE specialtyE : MedicalSpecialtyE.values()) {
            if (specialtyE.getName().equalsIgnoreCase(specialty)) {
                return specialtyE;
            }
        }
        return null;
    }

    public static List<String> getSpecialties() {
        return Arrays.stream(MedicalSpecialtyE.values())
                .map(MedicalSpecialtyE::getName)
                .collect(Collectors.toList());
    }
}
