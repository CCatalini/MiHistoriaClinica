package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.AnalysisModel;
import com.example.MiHistoriaClinica.model.MedicineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<AnalysisModel, Long> {

    /**
     * JOIN para unir la entidad PatientModel con la lista analysis y luego filtra los resultados por el ID del paciente
     */
    @Query("SELECT a FROM PatientModel p JOIN p.analysis a WHERE p.patientId = :patientId")
    List<AnalysisModel> getAnalysisByPatientId(@Param("patientId") Long id);





}
