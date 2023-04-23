package com.example.MiHistoriaClinica.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException() {
        super("Paciente no encontrado");
    }
}
