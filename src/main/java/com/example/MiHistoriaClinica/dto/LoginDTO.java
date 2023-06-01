package com.example.MiHistoriaClinica.dto;

public class LoginDTO{

    private Long dni;

    private String password;

    public LoginDTO(Long dni, String password) {
        this.dni = dni;
        this.password = password;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
