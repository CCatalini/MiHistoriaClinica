package com.example.MiHistoriaClinica.presentation.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientDTO {

    private String name;
    private String lastname;
    private String password;
    private Long dni;
    private String email;

    public PatientDTO() {
        // constructor sin argumentos
    }

    public PatientDTO(String name, String lastname, String password, Long dni, String email) {
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.dni = dni;
        this.email = email;
    }

}
