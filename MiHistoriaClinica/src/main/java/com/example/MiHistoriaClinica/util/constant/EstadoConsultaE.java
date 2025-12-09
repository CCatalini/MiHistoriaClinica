package com.example.MiHistoriaClinica.util.constant;

import lombok.Getter;

@Getter
public enum EstadoConsultaE {
    PENDIENTE("Pendiente"),
    REALIZADA("Realizada"),
    CANCELADA("Cancelada"),
    VENCIDO("Vencido");

    private final String name;

    EstadoConsultaE(String name) {
        this.name = name;
    }

    public static EstadoConsultaE getEnumFromName(String name) {
        for (EstadoConsultaE estado : EstadoConsultaE.values()) {
            if (estado.getName().equalsIgnoreCase(name)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("No se encontró el estado: " + name);
    }
}

