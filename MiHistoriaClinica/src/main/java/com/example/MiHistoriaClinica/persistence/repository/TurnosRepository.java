package com.example.MiHistoriaClinica.persistence.repository;

import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.model.Turnos;
import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
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
}
