package com.example.MiHistoriaClinica.util.exception;

/** Excepción personalizada para manejar errores cuando no se encuentra un recurso */

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
