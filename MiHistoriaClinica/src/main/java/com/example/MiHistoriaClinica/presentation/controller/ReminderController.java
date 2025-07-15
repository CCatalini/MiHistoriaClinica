package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.service.implementation.ReminderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reminders")
@CrossOrigin("*")
public class ReminderController {

    @Autowired
    private ReminderServiceImpl reminderService;

    @PostMapping("/turnos/send")
    public ResponseEntity<String> sendTurnoReminders() {
        try {
            reminderService.sendTurnoRemindersManual();
            return ResponseEntity.ok("Recordatorios de turnos enviados exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error enviando recordatorios de turnos: " + e.getMessage());
        }
    }

    @PostMapping("/analysis/send")
    public ResponseEntity<String> sendAnalysisReminders() {
        try {
            reminderService.sendAnalysisRemindersManual();
            return ResponseEntity.ok("Recordatorios de análisis enviados exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error enviando recordatorios de análisis: " + e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getReminderStatus() {
        try {
            reminderService.scheduleReminderJobs();
            return ResponseEntity.ok("Servicio de recordatorios funcionando correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error en el servicio de recordatorios: " + e.getMessage());
        }
    }
} 