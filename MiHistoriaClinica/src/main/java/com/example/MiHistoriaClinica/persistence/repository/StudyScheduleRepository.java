package com.example.MiHistoriaClinica.persistence.repository;

import com.example.MiHistoriaClinica.persistence.model.StudySchedule;
import com.example.MiHistoriaClinica.util.constant.AnalysisE;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Long> {
    
    // Buscar turnos disponibles por tipo de estudio
    List<StudySchedule> findByAnalysisTypeAndAvailableTrueAndFechaGreaterThanEqualOrderByFechaAscHoraAsc(
            AnalysisE analysisType, LocalDate fecha);
    
    // Buscar turnos disponibles por tipo y centro
    List<StudySchedule> findByAnalysisTypeAndMedicalCenterAndAvailableTrueAndFechaGreaterThanEqualOrderByFechaAscHoraAsc(
            AnalysisE analysisType, MedicalCenterE medicalCenter, LocalDate fecha);
    
    // Buscar turnos disponibles por tipo, centro y fecha específica
    List<StudySchedule> findByAnalysisTypeAndMedicalCenterAndFechaAndAvailableTrueOrderByHoraAsc(
            AnalysisE analysisType, MedicalCenterE medicalCenter, LocalDate fecha);
    
    // Obtener fechas con disponibilidad
    @Query("SELECT DISTINCT s.fecha FROM StudySchedule s WHERE s.analysisType = :type AND s.available = true AND s.fecha >= :fechaInicio ORDER BY s.fecha")
    List<LocalDate> findAvailableDatesByType(@Param("type") AnalysisE type, @Param("fechaInicio") LocalDate fechaInicio);
    
    // Obtener fechas con disponibilidad por centro
    @Query("SELECT DISTINCT s.fecha FROM StudySchedule s WHERE s.analysisType = :type AND s.medicalCenter = :center AND s.available = true AND s.fecha >= :fechaInicio ORDER BY s.fecha")
    List<LocalDate> findAvailableDatesByTypeAndCenter(
            @Param("type") AnalysisE type, 
            @Param("center") MedicalCenterE center,
            @Param("fechaInicio") LocalDate fechaInicio);
}

