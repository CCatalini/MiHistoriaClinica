package com.example.MiHistoriaClinica.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicDTO {

    private String name;
    private String lastname;
    private String password;
    private Long dni;
    private String email;
    private Long matricula;
    private String specialty;

    public MedicDTO (){

    }

    public MedicDTO(String name, String lastname, String password, Long dni, String email, Long matricula, String specialty) {
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.dni = dni;
        this.email = email;
        this.matricula = matricula;
        this.specialty = specialty;
    }

}
