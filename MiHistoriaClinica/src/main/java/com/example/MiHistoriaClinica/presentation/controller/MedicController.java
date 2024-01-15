package com.example.MiHistoriaClinica.presentation.controller;


import com.example.MiHistoriaClinica.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.MedicalHistory;
import com.example.MiHistoriaClinica.persistence.model.Medicine;
import com.example.MiHistoriaClinica.presentation.dto.*;
import com.example.MiHistoriaClinica.service.implementation.MedicServiceImpl;
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
@RequestMapping("/medic")
@CrossOrigin("*")
public class MedicController {


    private final MedicServiceImpl medicService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);


    @Autowired
    public MedicController(MedicServiceImpl medicService) {
        this.medicService = medicService;
    }


    @PostMapping("/signup")
    public ResponseEntity<Medic> createMedic (@RequestBody MedicDTO medicDTO) {

       Medic createdMedic = medicService.createMedic(medicDTO);
       return new ResponseEntity<>(createdMedic, HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenDTO> loginMedic (@RequestBody MedicLoginDTO medicDto){
        Medic loggedInMedic = medicService.loginMedic(medicDto);
        TokenDTO token = jwt.generateToken(loggedInMedic.getMedicId(), "MEDIC");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutMedic(@RequestHeader("Authorization") String token){
        return jwt.invalidateToken(token);
    }

    @GetMapping("/get-medic-info")
    public ResponseEntity<MedicDTO> getMedicInfo(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        MedicDTO medic = medicService.getMedicInfo(medicId);
        return new ResponseEntity<>(medic, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Medic>> getAllMedics(){
        List<Medic> medics = medicService.getAllMedics();
        return new ResponseEntity<>(medics, HttpStatus.OK);
    }


    /** MÉTODOS DEL MEDICO EN RELACIÓN AL PACIENTE                                                  */

    @PostMapping("/linkPatient")
    public ResponseEntity<Void> linkPatient(@RequestHeader("Authorization") String token, @RequestParam String linkCode) throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        medicService.linkPatient(linkCode, medicId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-patient-info")
    public ResponseEntity<PatientDTO> getPatientInfo(@RequestHeader("patientLinkCode") String patientLinkcode){
        PatientDTO patient = medicService.getPatientInfo(patientLinkcode);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping("/get-patients")
    public ResponseEntity<List<PatientDTO>> getPatients(@RequestHeader("Authorization") String token) throws InvalidTokenException {

        List<PatientDTO> patients = medicService.getPatientsDtoByMedicId(jwtValidator.getId(token));

        if(patients.isEmpty())  return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @DeleteMapping("/delete-patientLinkCode")
    public ResponseEntity<Void> deletePatientLinkCode(@RequestHeader("patientLinkCode") String patientLinkCode) {
        medicService.deletePatientLinkCode(patientLinkCode);
        return ResponseEntity.noContent().build();
    }


    /**Métodos Medical History*/

    @PostMapping("/create-medical-history")
    public ResponseEntity<MedicalHistory> createPatientMedicalHistory(@RequestHeader("Authorization") String token,
                                                                      @RequestHeader("patientLinkCode") String patientLinkCode,
                                                                      @RequestBody MedicalHistoryDTO medicalHistory)
                                                                           throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        MedicalHistory createdMedicalHistory = medicService.createPatientMedicalHistory(medicId, patientLinkCode, medicalHistory);

        if (createdMedicalHistory == null)      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else                                    return new ResponseEntity<>(createdMedicalHistory, HttpStatus.CREATED);

    }

    @GetMapping("/get-medical-history")
    public ResponseEntity<MedicalHistoryDTO> getPatientMedicalHistory(@RequestHeader("patientLinkCode") String patientLinkCode ) throws InvalidTokenException {
        MedicalHistoryDTO medicalHistory = medicService.getPatientMedicalHistory(patientLinkCode);
        return new ResponseEntity<>(medicalHistory, HttpStatus.OK);
    }

    @PostMapping("/update-medical-history")
    public ResponseEntity<Void> updateMedicalHistory(@RequestHeader("Authorization") String token,
                                                                    @RequestHeader("patientLinkCode") String patientLinkCode,
                                                                    @RequestBody MedicalHistoryDTO medicalHistory)
                                                                    throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        medicService.createPatientMedicalHistory(medicId, patientLinkCode, medicalHistory);

        return ResponseEntity.ok().build();
    }



    /**Métodos Medicine*/

    @PostMapping("/create-medicine")
    public ResponseEntity<Medicine> createPatientMedicine (@RequestHeader("Authorization") String token,
                                                           @RequestHeader("patientLinkCode") String patientLinkCode,
                                                           @RequestBody MedicineDTO medicine)
                                                                throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        Medicine createdMedicine = medicService.createPatientMedicine(medicId, patientLinkCode, medicine);

        if(createdMedicine == null)         return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else                                return new ResponseEntity<>(createdMedicine, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-medicine")
    public ResponseEntity<Void> deletePatientMedicine (@RequestHeader("patientLinkCode") String patientLinkCode,
                                                       @RequestParam("medicineId") Long medicineId){
        medicService.deletePatientMedicine(patientLinkCode, medicineId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-patient-medicines")
    public ResponseEntity<List<Medicine>> getPatientMedicines (@RequestHeader("patientLinkCode") String patientLinkCode) throws InvalidTokenException {
        List<Medicine> medicines = medicService.getMedicinesByPatientLinkCode(patientLinkCode);
        if (medicines==null)    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(medicines, HttpStatus.OK);

    }

    @GetMapping("/get-medicines-byStatus")
    public ResponseEntity<List<Medicine>> getPatientMedicinesByStatus(@RequestHeader("patientLinkCode") String patientLinkCode,
                                                                      @RequestParam("status") String status){
       List<Medicine> filteredMedicines = medicService.getAnalysisByStatus(patientLinkCode, status);
        return new ResponseEntity<>(filteredMedicines, HttpStatus.OK);
    }





}

