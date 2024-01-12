package com.example.MiHistoriaClinica.presentation.dto;

public class MedicalAppointmentDTO {

    private String appointmentReason;
    private String currentIllness;
    private String physicalExam;
    private String observations;

    public MedicalAppointmentDTO (){

    }

    public MedicalAppointmentDTO(String appointmentReason, String currentIllness, String physicalExam, String observations) {
        this.appointmentReason = appointmentReason;
        this.currentIllness = currentIllness;
        this.physicalExam = physicalExam;
        this.observations = observations;
    }

    public String getAppointmentReason() {
        return appointmentReason;
    }

    public void setAppointmentReason(String appointmentReason) {
        this.appointmentReason = appointmentReason;
    }

    public String getCurrentIllness() {
        return currentIllness;
    }

    public void setCurrentIllness(String currentIllness) {
        this.currentIllness = currentIllness;
    }

    public String getPhysicalExam() {
        return physicalExam;
    }

    public void setPhysicalExam(String physicalExam) {
        this.physicalExam = physicalExam;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
