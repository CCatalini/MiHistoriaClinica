package com.example.MiHistoriaClinica.dto;

public class MedicSignupDTO {

    private String name;
    private String lastname;
    private String password;
    private Long dni;
    private String email;
    private Long matricula;
    private String specialty;

    public MedicSignupDTO(String name, String lastname, String password, Long dni, String email, Long matricula, String specialty) {
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.dni = dni;
        this.email = email;
        this.matricula = matricula;
        this.specialty = specialty;
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

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
