package com.example.MiHistoriaClinica.exception;

public class MedicNotFoundException extends RuntimeException {

    public MedicNotFoundException() {
        super("Medico no encontrado");
    }

}
