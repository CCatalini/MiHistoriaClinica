package com.example.MiHistoriaClinica.presentation.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientDTO {

    private Long patientId;
    private String name;
    private String lastname;
    private String password;
    private Long dni;
    private String email;
    private String linkCode;

    public PatientDTO() {
        // constructor sin argumentos
    }

    public PatientDTO(String name, String lastname, Long dni, String email) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.email = email;
    }

    public PatientDTO(Long patientId, String name, String lastname, String password, Long dni, String email) {
        this.patientId = patientId;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.dni = dni;
        this.email = email;
    }

    public PatientDTO(Long patientId, String name, String lastname, Long dni, String email, String linkCode) {
        this.patientId = patientId;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.email = email;
        this.linkCode = linkCode;
    }

}
