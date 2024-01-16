package com.example.MiHistoriaClinica.util.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException() {
        super("Paciente no encontrado");
    }
}
