package com.example.MiHistoriaClinica.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientLoginDTO {

    private Long dni;
    private String password;

    public PatientLoginDTO(Long dni, String password) {
        this.dni = dni;
        this.password = password;
    }

}
