package com.example.MiHistoriaClinica.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicLoginDTO {

    private Long matricula;
    private String password;

    public MedicLoginDTO(Long matricula, String password) {
        this.matricula = matricula;
        this.password = password;
    }

}
