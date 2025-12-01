package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.persistence.model.MedicalAppointment;
import com.example.MiHistoriaClinica.util.constant.EstadoConsultaE;

import java.util.List;

public interface MedicalAppointmentService {
    
    boolean updateEstado(Long appointmentId, EstadoConsultaE nuevoEstado);
    
    List<MedicalAppointment> getAppointmentsByPatientIdAndEstado(Long patientId, EstadoConsultaE estado);
}
