package com.example.MiHistoriaClinica.util.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum BloodTypeE {
    A_POSITIVO("A+"),
    A_NEGATIVO("A-"),
    B_POSITIVO("B+"),
    B_NEGATIVO("B-"),
    AB_POSITIVO("AB+"),
    AB_NEGATIVO("AB-"),
    O_POSITIVO("O+"),
    O_NEGATIVO("O-");

    private final String type;

    BloodTypeE(String type) {
        this.type = type;
    }

    public static List<String> getTypes() {
        return Arrays.stream(BloodTypeE.values())
                .map(BloodTypeE::getType)
                .collect(Collectors.toList());
    }

    public static BloodTypeE getEnumFromName(String type) {
        for (BloodTypeE typeE : BloodTypeE.values()) {
            if (typeE.getType().equalsIgnoreCase(type)) {
                return typeE;
            }
        }
        return null;
    }


}
