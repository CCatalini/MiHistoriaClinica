package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.persistence.model.*;
import com.example.MiHistoriaClinica.persistence.repository.MedicRepository;
import com.example.MiHistoriaClinica.persistence.repository.PatientRepository;
import com.example.MiHistoriaClinica.persistence.repository.TurnosRepository;
import com.example.MiHistoriaClinica.presentation.dto.MedicalAppointmentDTO;
import com.example.MiHistoriaClinica.service.EmailService;
import com.example.MiHistoriaClinica.util.constant.EstadoConsultaE;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.service.implementation.MedicalAppointmentServiceImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicalAppointment")
@CrossOrigin("*")
public class MedicalAppointmentController {

    private final MedicalAppointmentServiceImpl medicalAppointmentService;
    private final PatientRepository patientRepository;
    private final MedicRepository medicRepository;
    private final TurnosRepository turnosRepository;
    private final EmailService emailService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);


    @Autowired
    public MedicalAppointmentController(MedicalAppointmentServiceImpl medicalAppointmentService,
                                        PatientRepository patientRepository,
                                        MedicRepository medicRepository,
                                        TurnosRepository turnosRepository,
                                        EmailService emailService) {
        this.medicalAppointmentService = medicalAppointmentService;
        this.patientRepository = patientRepository;
        this.medicRepository = medicRepository;
        this.turnosRepository = turnosRepository;
        this.emailService = emailService;
    }


    @PostMapping("/medic/create")
    public ResponseEntity<Void> createMedicalAppointment(@RequestHeader("Authorization") String tokenMedic,
                                                         @RequestHeader("patientLinkCode") String patientLinkCode,
                                                         @RequestBody MedicalAppointmentDTO appointmentDTO)
                                                         throws InvalidTokenException {
        Long medicId = jwtValidator.getId(tokenMedic);
        medicalAppointmentService.createMedicalAppointment(medicId, patientLinkCode, appointmentDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/medic/get")
    public ResponseEntity<List<MedicalAppointment>> getPatientAppointmentList (@RequestHeader("patientLinkCode") String patientLinkCode){
        List<MedicalAppointment> appointmentList = medicalAppointmentService.getAppointmentListByLinkCode(patientLinkCode);
        if (appointmentList == null || appointmentList.isEmpty())        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else                                                             return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    @GetMapping("/patient/get")
    public ResponseEntity<List<MedicalAppointment>> getAppointmentList(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        List<MedicalAppointment> appointmentList = medicalAppointmentService.getAppointmentListById(jwtValidator.getId(token));
        if(appointmentList == null || appointmentList.isEmpty())         return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else                                                             return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    @PutMapping("/update-estado/{appointmentId}")
    public ResponseEntity<String> updateEstado(@PathVariable Long appointmentId, 
                                               @RequestParam String estado) {
        try {
            EstadoConsultaE nuevoEstado = EstadoConsultaE.getEnumFromName(estado);
            boolean updated = medicalAppointmentService.updateEstado(appointmentId, nuevoEstado);
            if (updated) {
                return new ResponseEntity<>("Estado actualizado exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Consulta médica no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Estado inválido", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/patient/get-by-estado")
    public ResponseEntity<List<MedicalAppointment>> getAppointmentsByEstado(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String estado) throws InvalidTokenException {
        Long patientId = jwtValidator.getId(token);
        EstadoConsultaE estadoEnum = null;
        
        if (estado != null && !estado.isEmpty()) {
            try {
                estadoEnum = EstadoConsultaE.getEnumFromName(estado);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        
        List<MedicalAppointment> appointmentList = medicalAppointmentService.getAppointmentsByPatientIdAndEstado(patientId, estadoEnum);
        if (appointmentList == null || appointmentList.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    /**
     * Finaliza la consulta y envía email de resumen al paciente.
     * También actualiza el estado del turno a REALIZADA.
     */
    @PostMapping("/medic/finish-consultation")
    public ResponseEntity<String> finishConsultation(
            @RequestHeader("Authorization") String token,
            @RequestHeader("patientLinkCode") String patientLinkCode,
            @RequestHeader(value = "turnoId", required = false) Long turnoId,
            @RequestBody(required = false) ConsultationChanges changes) throws InvalidTokenException {
        
        Long medicId = jwtValidator.getId(token);
        
        // Obtener médico y paciente
        Optional<Medic> medicOpt = medicRepository.findById(medicId);
        Optional<Patient> patientOpt = patientRepository.findByLinkCode(patientLinkCode);
        
        if (medicOpt.isEmpty() || patientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico o paciente no encontrado");
        }
        
        // Actualizar estado del turno a REALIZADA si se proporciona el turnoId
        if (turnoId != null) {
            Optional<Turnos> turnoOpt = turnosRepository.findById(turnoId);
            if (turnoOpt.isPresent()) {
                Turnos turno = turnoOpt.get();
                turno.setEstadoConsulta(EstadoConsultaE.REALIZADA);
                turnosRepository.save(turno);
            }
        }
        
        // Extraer datos de cambios (si existen)
        List<String> estudios = changes != null ? changes.estudios : null;
        List<String> medicamentos = changes != null ? changes.medicamentos : null;
        boolean historiaActualizada = changes != null && changes.historiaActualizada;
        
        // Enviar email de resumen
        emailService.sendConsultationSummaryEmail(
            patientOpt.get(), 
            medicOpt.get(),
            estudios,
            medicamentos,
            historiaActualizada
        );
        
        return ResponseEntity.ok("Email de resumen enviado exitosamente");
    }
    
    // Clase simple para recibir los cambios
    public static class ConsultationChanges {
        public List<String> estudios;
        public List<String> medicamentos;
        public boolean historiaActualizada;
    }

}
