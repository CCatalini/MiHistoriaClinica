package com.example.MiHistoriaClinica.persistence.repository;

import com.example.MiHistoriaClinica.persistence.model.MedicalAppointment;
import com.example.MiHistoriaClinica.util.constant.EstadoConsultaE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointment, Long> {
    
    @Query("SELECT ma FROM MedicalAppointment ma WHERE ma.patient.patientId = :patientId AND ma.estado = :estado")
    List<MedicalAppointment> findByPatientIdAndEstado(@Param("patientId") Long patientId, @Param("estado") EstadoConsultaE estado);
    
    @Query("SELECT ma FROM MedicalAppointment ma WHERE ma.patient.patientId = :patientId")
    List<MedicalAppointment> findByPatientId(@Param("patientId") Long patientId);
}
