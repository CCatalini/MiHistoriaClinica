package com.example.MiHistoriaClinica.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicalAppointmentDTO {

    private String appointmentReason;
    private String currentIllness;
    private String physicalExam;
    private String observations;
    private String estado;

    public MedicalAppointmentDTO (){

    }

    public MedicalAppointmentDTO(String appointmentReason, String currentIllness, String physicalExam, String observations) {
        this.appointmentReason = appointmentReason;
        this.currentIllness = currentIllness;
        this.physicalExam = physicalExam;
        this.observations = observations;
    }

}
