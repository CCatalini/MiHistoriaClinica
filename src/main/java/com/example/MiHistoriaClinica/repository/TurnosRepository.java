package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.model.Turnos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnosRepository extends JpaRepository<Turnos, Long> {

    List<Turnos> findByPatient(PatientModel patient);
}
