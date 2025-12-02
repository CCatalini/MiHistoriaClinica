package com.example.MiHistoriaClinica.presentation.dto;

public class TurnoDisponibleDTO {
    private Long turnoId; // ID del turno para poder reservarlo
    private String fecha; // formato yyyy-MM-dd
    private String hora;  // formato HH:mm:ss

    public TurnoDisponibleDTO(Long turnoId, String fecha, String hora) {
        this.turnoId = turnoId;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Long getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(Long turnoId) {
        this.turnoId = turnoId;
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