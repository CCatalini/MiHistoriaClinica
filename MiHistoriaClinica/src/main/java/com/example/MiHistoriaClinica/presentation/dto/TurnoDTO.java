package com.example.MiHistoriaClinica.presentation.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class TurnoDTO {

    private LocalDate fechaTurno;
    private LocalTime horaTurno;


    public TurnoDTO(LocalDate fechaTurno, LocalTime horaTurno) {
        this.fechaTurno = fechaTurno;
        this.horaTurno = horaTurno;
    }

}
