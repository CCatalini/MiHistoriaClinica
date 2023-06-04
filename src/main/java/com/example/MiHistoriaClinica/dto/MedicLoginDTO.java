package com.example.MiHistoriaClinica.dto;

public class MedicLoginDTO {

    private Long matricula;
    private String password;

    public MedicLoginDTO(Long matricula, String password) {
        this.matricula = matricula;
        this.password = password;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
