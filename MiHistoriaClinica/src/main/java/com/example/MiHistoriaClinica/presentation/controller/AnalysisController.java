package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.presentation.dto.AnalysisDTO;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.persistence.model.Analysis;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.model.StudySchedule;
import com.example.MiHistoriaClinica.persistence.repository.PatientRepository;
import com.example.MiHistoriaClinica.persistence.repository.StudyScheduleRepository;
import com.example.MiHistoriaClinica.service.EmailService;
import com.example.MiHistoriaClinica.service.implementation.AnalysisServiceImpl;
import com.example.MiHistoriaClinica.util.constant.AnalysisE;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/analysis")
@CrossOrigin("*")
public class AnalysisController {

    private final AnalysisServiceImpl analysisService;
    private final PatientRepository patientRepository;
    private final StudyScheduleRepository studyScheduleRepository;
    private final EmailService emailService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);

    @Autowired
    public AnalysisController(AnalysisServiceImpl analysisService, PatientRepository patientRepository, 
                              StudyScheduleRepository studyScheduleRepository, EmailService emailService) {
        this.analysisService = analysisService;
        this.patientRepository = patientRepository;
        this.studyScheduleRepository = studyScheduleRepository;
        this.emailService = emailService;
    }

    @GetMapping("/all-names")
    public ResponseEntity<List<String>> getAllAnalysisNames(){
        List<String> analysisNames = analysisService.getAllAnalysisNames();
        return new ResponseEntity<>(analysisNames, HttpStatus.OK);
    }

    @GetMapping("/medicalCenter/all-names")
    public ResponseEntity<List<String>> getAllMedicalCenters() {
        List<String> analysisNames = analysisService.getAllMedicalCenters();
        return new ResponseEntity<>(analysisNames, HttpStatus.OK);

    }

    @GetMapping("/description")
    public ResponseEntity<String> getAnalysisDescription(@RequestParam("analysisName") String analysisName) {
        String description = analysisService.getAnalysisDescription(analysisName);
        return new ResponseEntity<>(description, HttpStatus.OK);
    }


    @PostMapping("/medic/create-patient-analysis")
    public ResponseEntity<Analysis> createPatientAnalysis(@RequestHeader("Authorization") String token,
                                                          @RequestHeader("patientLinkCode") String patientLinkCode,
                                                          @RequestBody AnalysisDTO analysisDTO)
                                                          throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        Analysis createdAnalysis = analysisService.createPatientAnalysis(medicId, patientLinkCode, analysisDTO);

        if (createdAnalysis == null)      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else                                    return new ResponseEntity<>(createdAnalysis, HttpStatus.CREATED);

    }

    @GetMapping("/medic/get-analysis")
    public ResponseEntity<List<Analysis>> getPatientAnalysisList(@RequestHeader("patientLinkCode") String patientLinkCode) {
        List<Analysis> analysisList = analysisService.getAnalyzesByPatientLinkCode(patientLinkCode);
        if (analysisList == null)       return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return                                 new ResponseEntity<>(analysisList, HttpStatus.OK);
    }

    @DeleteMapping("/medic/delete-analysis")
    public ResponseEntity<Void> deletePatientAnalysis(@RequestHeader("patientLinkCode") String patientLinkCode,
                                                      @RequestParam("analysisId") Long analysisId){
        analysisService.deletePatientAnalysis(patientLinkCode, analysisId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/medic/get-analysis-byStatus")
    public ResponseEntity<List<Analysis>> getPatientAnalysisByStatus(@RequestHeader("patientLinkCode") String patientLinkCode,
                                                                     @RequestParam("status") String status){
        Optional<Patient> patientModel = patientRepository.findByLinkCode(patientLinkCode);
        List<Analysis> filteredAnalysis = analysisService.getAnalysisByStatus(patientModel.get().getPatientId(), status);
        return new ResponseEntity<>(filteredAnalysis, HttpStatus.OK);
    }



    @GetMapping("/patient/get-analysis")
    public ResponseEntity<List<Analysis>> getMyAnalysisList(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        List<Analysis> analysisList = analysisService.getAnalysisByPatientId(jwtValidator.getId(token));
        return new ResponseEntity<>(analysisList, HttpStatus.OK);
    }

    @PutMapping("/patient/update-analysis-status")
    public ResponseEntity<String> updateAnalysisStatus(@RequestHeader("analysis_id") Long analysisId,
                                                       @RequestParam("status") String status){
        Analysis analysis = analysisService.getAnalysisByAnalysisId(analysisId);
        if (analysis == null)       return new ResponseEntity<>("AnalysisE no disponible", HttpStatus.NOT_FOUND);

        analysis.setStatus(status);
        analysisService.saveAnalysis(analysis);

        return ResponseEntity.ok("Estado actualizado");
    }

    @GetMapping("/patient/get-analysis-byStatus")
    public ResponseEntity<List<Analysis>> getAnalysisByStatus(@RequestHeader("Authorization") String token,
                                                              @RequestParam("status") String status) throws InvalidTokenException {
        List<Analysis> filteredAnalysis = analysisService.getAnalysisByStatus(jwtValidator.getId(token), status);
        return new ResponseEntity<>(filteredAnalysis, HttpStatus.OK);
    }

    @GetMapping("/patient/get-analysis-by-medicalCenter")
    public ResponseEntity<List<Analysis>> getAnalysisByMedicalCenter(@RequestHeader("Authorization") String token,
                                                                     @RequestParam("medicalCenter") String medicalCenter) throws InvalidTokenException {
        List<Analysis> filteredAnalysis = analysisService.getAnalysisByMedicalCenter(jwtValidator.getId(token), medicalCenter);
        return new ResponseEntity<>(filteredAnalysis, HttpStatus.OK);
    }

    // ==================== DISPONIBILIDAD DE TURNOS ====================

    /**
     * Obtiene fechas disponibles para un tipo de estudio
     */
    @GetMapping("/schedule/available-dates")
    public ResponseEntity<List<LocalDate>> getAvailableDates(
            @RequestParam String analysisType,
            @RequestParam(required = false) String medicalCenter) {
        
        AnalysisE type = AnalysisE.getEnumFromName(analysisType);
        if (type == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<LocalDate> dates;
        if (medicalCenter != null && !medicalCenter.isEmpty()) {
            MedicalCenterE center = MedicalCenterE.getEnumFromName(medicalCenter);
            if (center == null) return ResponseEntity.badRequest().build();
            dates = studyScheduleRepository.findAvailableDatesByTypeAndCenter(type, center, LocalDate.now());
        } else {
            dates = studyScheduleRepository.findAvailableDatesByType(type, LocalDate.now());
        }
        
        return ResponseEntity.ok(dates);
    }

    /**
     * Obtiene turnos disponibles para un tipo de estudio, centro y fecha
     */
    @GetMapping("/schedule/available")
    public ResponseEntity<List<StudySchedule>> getAvailableSchedules(
            @RequestParam String analysisType,
            @RequestParam String medicalCenter,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        
        AnalysisE type = AnalysisE.getEnumFromName(analysisType);
        MedicalCenterE center = MedicalCenterE.getEnumFromName(medicalCenter);
        
        if (type == null || center == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<StudySchedule> schedules = studyScheduleRepository
                .findByAnalysisTypeAndMedicalCenterAndFechaAndAvailableTrueOrderByHoraAsc(type, center, fecha);
        
        return ResponseEntity.ok(schedules);
    }

    /**
     * Reserva un turno para un estudio del paciente
     */
    @PostMapping("/schedule/reserve")
    public ResponseEntity<String> reserveSchedule(
            @RequestHeader("Authorization") String token,
            @RequestParam Long scheduleId,
            @RequestParam Long analysisId) throws InvalidTokenException {
        
        Long patientId = jwtValidator.getId(token);
        
        // Buscar el turno
        Optional<StudySchedule> scheduleOpt = studyScheduleRepository.findById(scheduleId);
        if (scheduleOpt.isEmpty() || !scheduleOpt.get().isAvailable()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Turno no disponible");
        }
        
        // Buscar el análisis
        Analysis analysis = analysisService.getAnalysisByAnalysisId(analysisId);
        if (analysis == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Buscar el paciente para enviar email
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        
        StudySchedule schedule = scheduleOpt.get();
        
        // Marcar turno como reservado
        schedule.setAvailable(false);
        schedule.setPatientId(patientId);
        schedule.setAnalysisId(analysisId);
        studyScheduleRepository.save(schedule);
        
        // Actualizar el análisis con la fecha/hora/centro
        analysis.setScheduledDate(schedule.getFecha());
        analysis.setScheduledTime(schedule.getHora());
        analysis.setMedicalCenterE(schedule.getMedicalCenter());
        analysis.setScheduleId(schedule.getId());
        analysis.setStatus("Programado");
        analysisService.saveAnalysis(analysis);
        
        // Enviar email de confirmación
        if (patientOpt.isPresent()) {
            emailService.sendAnalysisScheduledEmail(patientOpt.get(), analysis);
        }
        
        return ResponseEntity.ok("Turno reservado exitosamente");
    }

    /**
     * Cancela una reserva de turno
     */
    @DeleteMapping("/schedule/cancel")
    public ResponseEntity<String> cancelSchedule(
            @RequestHeader("Authorization") String token,
            @RequestParam Long analysisId) throws InvalidTokenException {
        
        Long patientId = jwtValidator.getId(token);
        
        // Buscar el análisis
        Analysis analysis = analysisService.getAnalysisByAnalysisId(analysisId);
        if (analysis == null || analysis.getScheduleId() == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Buscar y liberar el turno
        Optional<StudySchedule> scheduleOpt = studyScheduleRepository.findById(analysis.getScheduleId());
        if (scheduleOpt.isPresent()) {
            StudySchedule schedule = scheduleOpt.get();
            schedule.setAvailable(true);
            schedule.setPatientId(null);
            schedule.setAnalysisId(null);
            studyScheduleRepository.save(schedule);
        }
        
        // Actualizar el análisis
        analysis.setScheduledDate(null);
        analysis.setScheduledTime(null);
        analysis.setScheduleId(null);
        analysis.setStatus("Pendiente");
        analysisService.saveAnalysis(analysis);
        
        return ResponseEntity.ok("Reserva cancelada");
    }

    /**
     * Obtiene tipos de estudio disponibles (devuelve nombres de enums para compatibilidad con API)
     */
    @GetMapping("/get-all-analysis-names")
    public ResponseEntity<List<String>> getAnalysisNames() {
        return ResponseEntity.ok(AnalysisE.getEnumNames());
    }

}
