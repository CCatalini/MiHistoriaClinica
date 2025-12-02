package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.persistence.model.Analysis;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.model.Turnos;
import com.example.MiHistoriaClinica.persistence.repository.AnalysisRepository;
import com.example.MiHistoriaClinica.persistence.repository.TurnosRepository;
import com.example.MiHistoriaClinica.service.EmailService;
import com.example.MiHistoriaClinica.service.ReminderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {

    private static final Logger logger = LoggerFactory.getLogger(ReminderServiceImpl.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private TurnosRepository turnosRepository;

    @Autowired
    private AnalysisRepository analysisRepository;

    @Value("${app.reminders.enabled:true}")
    private boolean remindersEnabled;

    @Value("${app.reminders.turno.hours-before:24}")
    private int turnoHoursBefore;

    @Value("${app.reminders.analysis.hours-before:24}")
    private int analysisHoursBefore;

    @Override
    @Scheduled(cron = "0 0 9 * * *") // Ejecutar todos los días a las 9:00 AM
    public void sendTurnoReminders() {
        if (!remindersEnabled) {
            logger.info("Recordatorios de turnos deshabilitados");
            return;
        }

        logger.info("Iniciando proceso de envío de recordatorios de turnos");
        
        try {
            // Buscar turnos para mañana (24 horas antes)
            LocalDate tomorrowDate = LocalDate.now().plusDays(1);
            
            List<Turnos> turnosForTomorrow = turnosRepository.findByFechaTurnoAndAvailableFalse(tomorrowDate);
            
            logger.info("Encontrados {} turnos para mañana", turnosForTomorrow.size());
            
            for (Turnos turno : turnosForTomorrow) {
                if (turno.getPatient() != null && turno.getPatient().getEmail() != null) {
                    try {
                        emailService.sendTurnoReminderEmail(turno.getPatient(), turno);
                        logger.info("Recordatorio enviado exitosamente para turno ID: {} - Paciente: {}", 
                                   turno.getTurnoId(), turno.getPatient().getName());
                    } catch (Exception e) {
                        logger.error("Error enviando recordatorio para turno ID: {}", turno.getTurnoId(), e);
                    }
                } else {
                    logger.warn("Turno sin paciente o email: {}", turno.getTurnoId());
                }
            }
            
            logger.info("Proceso de recordatorios de turnos completado");
            
        } catch (Exception e) {
            logger.error("Error durante el proceso de recordatorios de turnos", e);
        }
    }

    @Override
    @Scheduled(cron = "0 0 10 * * *") // Ejecutar todos los días a las 10:00 AM
    public void sendAnalysisReminders() {
        if (!remindersEnabled) {
            logger.info("Recordatorios de análisis deshabilitados");
            return;
        }

        logger.info("Iniciando proceso de envío de recordatorios de análisis");
        
        try {
            // Buscar análisis pendientes (que llevan más de 24 horas pendientes)
            List<Analysis> pendingAnalysis = analysisRepository.findByStatus("Pendiente");
            
            logger.info("Encontrados {} análisis pendientes", pendingAnalysis.size());
            
            for (Analysis analysis : pendingAnalysis) {
                if (analysis.getPatients() != null && !analysis.getPatients().isEmpty()) {
                    for (Patient patient : analysis.getPatients()) {
                        if (patient.getEmail() != null) {
                            try {
                                // TODO: Implementar método sendAnalysisReminderEmail en EmailService
                                // emailService.sendAnalysisReminderEmail(patient, analysis);
                                logger.info("Recordatorio de análisis pendiente para paciente: {} - Análisis: {}", 
                                           patient.getName(), analysis.getName().getName());
                            } catch (Exception e) {
                                logger.error("Error enviando recordatorio de análisis para paciente: {}", 
                                           patient.getName(), e);
                            }
                        } else {
                            logger.warn("Paciente sin email: {}", patient.getName());
                        }
                    }
                } else {
                    logger.warn("Análisis sin pacientes asociados: {}", analysis.getAnalysis_id());
                }
            }
            
            logger.info("Proceso de recordatorios de análisis completado");
            
        } catch (Exception e) {
            logger.error("Error durante el proceso de recordatorios de análisis", e);
        }
    }

    @Override
    public void scheduleReminderJobs() {
        logger.info("Servicio de recordatorios iniciado. Configuración:");
        logger.info("- Recordatorios habilitados: {}", remindersEnabled);
        logger.info("- Horas antes para recordatorio de turnos: {}", turnoHoursBefore);
        logger.info("- Horas antes para recordatorio de análisis: {}", analysisHoursBefore);
        logger.info("- Recordatorios de turnos: todos los días a las 9:00 AM");
        logger.info("- Recordatorios de análisis: todos los días a las 10:00 AM");
    }

    // Método manual para enviar recordatorios (útil para testing)
    public void sendTurnoRemindersManual() {
        logger.info("Enviando recordatorios de turnos manualmente");
        sendTurnoReminders();
    }

    // Método manual para enviar recordatorios (útil para testing)
    public void sendAnalysisRemindersManual() {
        logger.info("Enviando recordatorios de análisis manualmente");
        sendAnalysisReminders();
    }
} 