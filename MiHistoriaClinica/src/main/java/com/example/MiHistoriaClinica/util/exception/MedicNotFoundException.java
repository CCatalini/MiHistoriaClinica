package com.example.MiHistoriaClinica.util.exception;

public class MedicNotFoundException extends RuntimeException {

    public MedicNotFoundException() {
        super("Medico no encontrado");
    }

}
