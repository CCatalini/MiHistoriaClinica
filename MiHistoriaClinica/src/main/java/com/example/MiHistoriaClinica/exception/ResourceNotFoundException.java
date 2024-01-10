package com.example.MiHistoriaClinica.exception;

/** excepción personalizada para manejar errores cuando no se encuentra un recurso */

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
