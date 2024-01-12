package com.example.MiHistoriaClinica.persistence.repository;

import com.example.MiHistoriaClinica.persistence.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    /**
     * JOIN para unir la entidad PatientModel con la lista analysis y luego filtra los resultados por el ID del paciente
     */
    @Query("SELECT a FROM Patient p JOIN p.analysis a WHERE p.patientId = :patientId")
    List<Analysis> getAnalysisByPatientId(@Param("patientId") Long id);





}
