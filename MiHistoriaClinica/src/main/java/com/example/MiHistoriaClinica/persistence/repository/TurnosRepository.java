package com.example.MiHistoriaClinica.persistence.repository;

import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.model.Turnos;
import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import com.example.MiHistoriaClinica.util.constant.EstadoConsultaE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnosRepository extends JpaRepository<Turnos, Long> {

    List<Turnos> findByPatient(Patient patient);
    List<Turnos> findByMedicAndAvailableTrue(Medic medic);
    List<Turnos> findByMedic_MedicIdAndAvailableTrue(Long medicId);
    List<Turnos> findByMedicSpecialtyAndFechaTurnoAndAvailableTrue(MedicalSpecialtyE specialty, java.time.LocalDate fechaTurno);
    List<Turnos> findByMedicSpecialtyAndFechaTurnoBetweenAndAvailableTrue(MedicalSpecialtyE specialty, java.time.LocalDate start, java.time.LocalDate end);
    List<Turnos> findByMedic_MedicIdAndAvailableFalseOrderByFechaTurnoAscHoraTurnoAsc(Long medicId);
    List<Turnos> findByMedic_MedicId(Long medicId);
    boolean existsByMedic_MedicIdAndFechaTurnoAndHoraTurno(Long medicId, java.time.LocalDate fechaTurno, java.time.LocalTime horaTurno);
    
    // Método para recordatorios - buscar turnos reservados para una fecha específica
    List<Turnos> findByFechaTurnoAndAvailableFalse(java.time.LocalDate fechaTurno);
    
    // Método para validar si el paciente ya tiene un turno en esa fecha y hora
    boolean existsByPatientAndFechaTurnoAndHoraTurno(Patient patient, java.time.LocalDate fechaTurno, java.time.LocalTime horaTurno);
    
    // Método alternativo usando patient_id
    boolean existsByPatient_PatientIdAndFechaTurnoAndHoraTurno(Long patientId, java.time.LocalDate fechaTurno, java.time.LocalTime horaTurno);
    
    // Todos los turnos reservados del día para un médico (ordenados por hora)
    List<Turnos> findByMedic_MedicIdAndFechaTurnoAndAvailableFalseOrderByHoraTurnoAsc(Long medicId, java.time.LocalDate fechaTurno);
    
    // Turnos pendientes del día para un médico (reservados, con estado PENDIENTE, ordenados por hora)
    List<Turnos> findByMedic_MedicIdAndFechaTurnoAndAvailableFalseAndEstadoConsultaOrderByHoraTurnoAsc(
            Long medicId, java.time.LocalDate fechaTurno, EstadoConsultaE estadoConsulta);
    
    // Turnos disponibles (vacíos) de un médico para una fecha específica
    List<Turnos> findByMedic_MedicIdAndFechaTurnoAndAvailableTrue(Long medicId, java.time.LocalDate fechaTurno);
    
    // Turnos disponibles (vacíos) anteriores a una fecha
    List<Turnos> findByMedic_MedicIdAndFechaTurnoBeforeAndAvailableTrue(Long medicId, java.time.LocalDate fecha);
    
    // Eliminar turnos disponibles (vacíos) anteriores a una fecha para un médico
    void deleteByMedic_MedicIdAndFechaTurnoBeforeAndAvailableTrue(Long medicId, java.time.LocalDate fecha);
}
