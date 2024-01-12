package com.example.MiHistoriaClinica.presentation.dto;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
