package com.example.MiHistoriaClinica.persistence.repository;

import com.example.MiHistoriaClinica.persistence.model.PatientModel;
import com.example.MiHistoriaClinica.persistence.model.Turnos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnosRepository extends JpaRepository<Turnos, Long> {

    List<Turnos> findByPatient(PatientModel patient);
}
