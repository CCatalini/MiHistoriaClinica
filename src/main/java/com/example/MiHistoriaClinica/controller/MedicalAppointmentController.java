package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.dto.MedicalAppointmentDTO;
import com.example.MiHistoriaClinica.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.model.MedicalAppointmentModel;
import com.example.MiHistoriaClinica.service.MedicalAppointmentServiceImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalAppointment")
@CrossOrigin("*")
public class MedicalAppointmentController {

    private final MedicalAppointmentServiceImpl medicalAppointmentService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);


    @Autowired
    public MedicalAppointmentController(MedicalAppointmentServiceImpl medicalAppointmentService){
        this.medicalAppointmentService = medicalAppointmentService;
    }


    @PostMapping("/medic/create")
    public ResponseEntity<Void> createMedicalAppointment(@RequestHeader("Authorization") String tokenMedic,
                                                                            @RequestHeader("patientLinkCode") String patientLinkCode,
                                                                            @RequestBody MedicalAppointmentDTO appointmentDTO) throws InvalidTokenException {
        Long medicId = jwtValidator.getId(tokenMedic);
        medicalAppointmentService.createMedicalAppointment(medicId, patientLinkCode, appointmentDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/medic/get")
    public ResponseEntity<List<MedicalAppointmentModel>> getPatientAppointmentList (@RequestHeader("patientLinkCode") String patientLinkCode){

        List<MedicalAppointmentModel> appointmentList = medicalAppointmentService.getAppointmentListByLinkCode(patientLinkCode);
        if (appointmentList == null || appointmentList.isEmpty())        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else                                                             return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    @GetMapping("/patient/get")
    public ResponseEntity<List<MedicalAppointmentModel>> getAppointmentList(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        List<MedicalAppointmentModel> appointmentList = medicalAppointmentService.getAppointmentListById(jwtValidator.getId(token));
        if(appointmentList == null || appointmentList.isEmpty())         return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else                                                             return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }













}






