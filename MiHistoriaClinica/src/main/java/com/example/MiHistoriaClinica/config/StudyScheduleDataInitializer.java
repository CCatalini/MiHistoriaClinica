package com.example.MiHistoriaClinica.config;

import com.example.MiHistoriaClinica.persistence.model.StudySchedule;
import com.example.MiHistoriaClinica.persistence.repository.StudyScheduleRepository;
import com.example.MiHistoriaClinica.util.constant.AnalysisE;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Inicializador de datos para turnos de estudios médicos.
 * Genera automáticamente 3 meses de disponibilidad para todos los estudios
 * en todos los centros médicos.
 * 
 * Configuración:
 * - 2 días por semana (Lunes y Miércoles)
 * - 5 turnos por día por centro médico
 * - 3 meses de cobertura desde la fecha actual
 */
@Component
public class StudyScheduleDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StudyScheduleDataInitializer.class);
    
    private final StudyScheduleRepository studyScheduleRepository;
    
    // Configuración de horarios por tipo de estudio
    private static final LocalTime[][] SCHEDULE_TIMES = {
        // Estudios de sangre (madrugada/mañana temprano)
        {LocalTime.of(7, 0), LocalTime.of(7, 30), LocalTime.of(8, 0), LocalTime.of(8, 30), LocalTime.of(9, 0)},
        // Estudios generales (mañana)
        {LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0), LocalTime.of(12, 0)},
        // Estudios de imagen (mañana/tarde)
        {LocalTime.of(9, 0), LocalTime.of(10, 30), LocalTime.of(12, 0), LocalTime.of(14, 0), LocalTime.of(15, 30)},
        // Estudios tarde
        {LocalTime.of(14, 0), LocalTime.of(14, 30), LocalTime.of(15, 0), LocalTime.of(15, 30), LocalTime.of(16, 0)}
    };

    public StudyScheduleDataInitializer(StudyScheduleRepository studyScheduleRepository) {
        this.studyScheduleRepository = studyScheduleRepository;
    }

    @Override
    public void run(String... args) {
        // Solo inicializar si la tabla está vacía
        if (studyScheduleRepository.count() > 0) {
            logger.info("StudySchedule ya tiene datos. Saltando inicialización.");
            return;
        }

        logger.info("Inicializando datos de turnos de estudios médicos...");
        
        List<StudySchedule> allSchedules = new ArrayList<>();
        
        // Fecha de inicio y fin (3 meses desde hoy)
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(3);
        
        // Generar para cada tipo de estudio
        for (AnalysisE analysisType : AnalysisE.values()) {
            // Generar para cada centro médico
            for (MedicalCenterE medicalCenter : MedicalCenterE.values()) {
                allSchedules.addAll(
                    generateSchedulesForStudy(analysisType, medicalCenter, startDate, endDate)
                );
            }
        }
        
        // Guardar todos los turnos
        studyScheduleRepository.saveAll(allSchedules);
        
        logger.info("Se generaron {} turnos de estudios médicos.", allSchedules.size());
    }

    /**
     * Genera los turnos para un tipo de estudio y centro médico específico.
     */
    private List<StudySchedule> generateSchedulesForStudy(
            AnalysisE analysisType, 
            MedicalCenterE medicalCenter,
            LocalDate startDate, 
            LocalDate endDate) {
        
        List<StudySchedule> schedules = new ArrayList<>();
        LocalTime[] times = getTimesForAnalysis(analysisType);
        
        LocalDate currentDate = startDate;
        
        while (!currentDate.isAfter(endDate)) {
            // Solo Lunes y Miércoles
            if (currentDate.getDayOfWeek() == DayOfWeek.MONDAY || 
                currentDate.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
                
                // 5 turnos por día
                for (LocalTime time : times) {
                    StudySchedule schedule = new StudySchedule();
                    schedule.setAnalysisType(analysisType);
                    schedule.setMedicalCenter(medicalCenter);
                    schedule.setFecha(currentDate);
                    schedule.setHora(time);
                    schedule.setAvailable(true);
                    schedules.add(schedule);
                }
            }
            currentDate = currentDate.plusDays(1);
        }
        
        return schedules;
    }

    /**
     * Retorna los horarios apropiados según el tipo de estudio.
     */
    private LocalTime[] getTimesForAnalysis(AnalysisE analysisType) {
        switch (analysisType) {
            // Estudios de sangre - requieren ayuno (madrugada)
            case HEMOGRAMA_COMPLETO:
            case PERFIL_LIPIDICO:
            case GLUCEMIA_EN_AYUNAS:
            case ANALISIS_DE_TIROIDES:
            case TEST_DE_VIH:
            case ANALISIS_DE_HEPATITIS:
                return SCHEDULE_TIMES[0];
            
            // Estudios de imagen complejos (horarios más espaciados)
            case TOMOGRAFIA_COMPUTARIZADA:
            case RESONANCIA_MAGNETICA:
            case ECOGRAFIA_ABDOMINAL:
            case COLONOSCOPIA:
                return SCHEDULE_TIMES[2];
            
            // Estudios de tarde
            case TEST_DE_GLUCOSA_POSPRANDIAL:
            case DENSITOMETRIA_OSEA:
                return SCHEDULE_TIMES[3];
            
            // Estudios generales (horarios estándar de mañana)
            default:
                return SCHEDULE_TIMES[1];
        }
    }
}
