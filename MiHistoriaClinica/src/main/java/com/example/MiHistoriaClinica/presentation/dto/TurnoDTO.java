package com.example.MiHistoriaClinica.presentation.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalTime;

public class TurnoDTO {

    private LocalDate fechaTurno;
    private LocalTime horaTurno;


    public TurnoDTO(LocalDate fechaTurno, LocalTime horaTurno) {
        this.fechaTurno = fechaTurno;
        this.horaTurno = horaTurno;
    }

    public LocalDate getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(LocalDate fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public LocalTime getHoraTurno() {
        return horaTurno;
    }

    public void setHoraTurno(LocalTime horaTurno) {
        this.horaTurno = horaTurno;
    }
}
