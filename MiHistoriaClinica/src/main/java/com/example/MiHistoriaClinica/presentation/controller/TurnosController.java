package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.persistence.model.Turnos;
import com.example.MiHistoriaClinica.service.implementation.PatientServiceImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.MiHistoriaClinica.presentation.dto.MedicTurnosDTO;

@RestController
@RequestMapping("/turno")
@CrossOrigin("*")
public class TurnosController {

    private final PatientServiceImpl patientService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);

    public TurnosController(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }


    @PostMapping("/patient/reserve-turno")
    public ResponseEntity<Void> reserveTurno (@RequestHeader("Authorization") String token,
                                             @RequestParam("turnoId") Long turnoId) throws InvalidTokenException {
        Long patientId = jwtValidator.getId(token);
        patientService.reserveTurno(patientId, turnoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/patient/available")
    public ResponseEntity<List<Turnos>> getAvailableTurnos (@RequestParam("medicId") Long medicId) {
        List<Turnos> available = patientService.getAvailableTurnosByMedic(medicId);
        return new ResponseEntity<>(available, HttpStatus.OK);
    }

    @GetMapping("/patient/available-by-specialty")
    public ResponseEntity<List<MedicTurnosDTO>> getAvailableBySpecialty(@RequestParam("specialty") String specialty,
                                                                       @RequestParam("date") String date){
        List<MedicTurnosDTO> result = patientService.searchAvailableTurnosBySpecialtyAndDate(specialty, date);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/patient/available-by-specialty-range")
    public ResponseEntity<List<MedicTurnosDTO>> getAvailableBySpecialtyRange(
            @RequestParam("specialty") String specialty,
            @RequestParam("startDate") String startDate) {
        List<MedicTurnosDTO> result = patientService.searchAvailableTurnosBySpecialtyAndDateRange(specialty, startDate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/patient/medics-with-available-turnos")
    public ResponseEntity<List<String>> getMedicsWithAvailableTurnosBySpecialty(
            @RequestParam("specialty") String specialty,
            @RequestParam("startDate") String startDate) {
        List<String> medics = patientService.getMedicsWithAvailableTurnosBySpecialty(specialty, startDate);
        return new ResponseEntity<>(medics, HttpStatus.OK);
    }

    @GetMapping("/patient/get-turnos")
    public ResponseEntity<List<Turnos>> getMisTurnos (@RequestHeader("Authorization") String token) throws InvalidTokenException {
        List<Turnos> misTurnos = patientService.getMisTurnos(jwtValidator.getId(token));
        return new ResponseEntity<>(misTurnos, HttpStatus.OK);
    }

    @DeleteMapping("/patient/delete-turno")
    public ResponseEntity<Void> deleteTurno (@RequestParam("turnoId") Long id){
        patientService.deleteTurno(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/medic/liberar-turno")
    public ResponseEntity<Void> liberarTurno (@RequestParam("turnoId") Long turnoId){
        patientService.liberarTurno(turnoId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/medic/bloquear-turno")
    public ResponseEntity<Void> bloquearTurno (@RequestParam("turnoId") Long turnoId){
        patientService.bloquearTurno(turnoId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/medic/reservar-turno")
    public ResponseEntity<Void> reservarTurnoParaPaciente (@RequestParam("turnoId") Long turnoId,
                                                           @RequestParam("patientDni") Long patientDni){
        patientService.reserveTurnoByDni(patientDni, turnoId);
        return ResponseEntity.ok().build();
    }
}
