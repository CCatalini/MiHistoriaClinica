package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.MedicalAppointment;

import java.util.List;

public interface MedicalAppointmentService {

    List<MedicalAppointment> getAppointmentsByPatientId(Long patientId);
}
