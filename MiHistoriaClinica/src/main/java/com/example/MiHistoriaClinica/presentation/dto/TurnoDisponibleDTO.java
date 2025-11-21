package com.example.MiHistoriaClinica.presentation.dto;

public class TurnoDisponibleDTO {
    private String fecha; // formato yyyy-MM-dd
    private String hora;  // formato HH:mm:ss

    public TurnoDisponibleDTO(String fecha, String hora) {
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
} 