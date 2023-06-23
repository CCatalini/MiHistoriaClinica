package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.dto.MedicalHistoryDTO;
import com.example.MiHistoriaClinica.dto.PatientLoginDTO;
import com.example.MiHistoriaClinica.dto.PatientDTO;
import com.example.MiHistoriaClinica.dto.TokenDTO;
import com.example.MiHistoriaClinica.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.MedicineModel;
import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.service.PatientServiceImpl;
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
@RequestMapping("/patient")
@CrossOrigin("*")
public class PatientController {

    private final PatientServiceImpl patientService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);

    @Autowired
    public PatientController(PatientServiceImpl patientService){
        this.patientService = patientService;

    }

    @PostMapping("/signup")
    public ResponseEntity<PatientModel> createPatient(@RequestBody PatientDTO patient) {
        PatientModel createdPatient = patientService.createPatient(patient);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenDTO> loginPatient(@RequestBody PatientLoginDTO patient) {
        PatientModel loggedInPatient = patientService.loginPatient(patient);
        TokenDTO token = jwt.generateToken(loggedInPatient.getPatientId(), "PATIENT");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutPatient(@RequestHeader("Authorization") String token) {
        return jwt.invalidateToken(token);
    }

    @GetMapping("/get-patient-info")
    public ResponseEntity<PatientDTO> getPatientInfo (@RequestHeader("Authorization") String token) throws InvalidTokenException {
        Long patientId = jwtValidator.getId(token);
        PatientDTO patient = patientService.getPatientInfo(patientId);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }


    @PostMapping("/generate-link-code")
    @ResponseBody
    public ResponseEntity<String> generateLinkCode(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        String linkCode = patientService.generateLinkCode(jwtValidator.getId(token));
        return ResponseEntity.ok(linkCode);
    }


    /** OK */
    @GetMapping("/get-medics")
    public ResponseEntity<List<MedicModel>> getMedics(@RequestHeader("Authorization") String token ) throws InvalidTokenException {
        List<MedicModel> medics = patientService.getMedicsByPatientId(jwtValidator.getId(token));
        return new ResponseEntity<>(medics, HttpStatus.OK);
    }

    /** OK */
    @GetMapping("/get-medicines")
    public ResponseEntity<List<MedicineModel>> getMedicines (@RequestHeader("Authorization") String token ) throws InvalidTokenException {
        List<MedicineModel> medicines = patientService.getMedicinesByPatientId(jwtValidator.getId(token));
        return new ResponseEntity<>(medicines, HttpStatus.OK);
    }

    @GetMapping("/get-medical-history")
    public ResponseEntity<MedicalHistoryDTO> getMedicalHistory(@RequestHeader("Authorization") String token ) throws InvalidTokenException {
        MedicalHistoryDTO medicalHistory = patientService.getMedicalHistory(jwtValidator.getId(token));
        return new ResponseEntity<>(medicalHistory, HttpStatus.OK);
    }

    @PutMapping("/update-medicine-status")
    public ResponseEntity<String> updateMedicineStatus(@RequestParam("medicineId") Long medicineId,
                                                       @RequestParam("status") String status) {

        MedicineModel medicine = patientService.getMedicineByMedicineId(medicineId);

        if (medicine == null )      return new ResponseEntity<>("Medicamento no encontrado", HttpStatus.NOT_FOUND);

        medicine.setStatus(status);
        patientService.saveMedicine(medicine);

        return ResponseEntity.ok("Estado del medicamento actualizado correctamente");
    }


}

/*n
    @GetMapping("/getById/{id}")
    public ResponseEntity<PatientModel> getPatientById(@PathVariable Long id) {
        PatientModel patient = patientService.getPatientById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping("/getByDni/{dni}")
    public ResponseEntity<PatientModel> getPatientByDni(@PathVariable Long dni) {
        PatientModel patient = patientService.getPatientByDni(dni);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

        @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteByDni/{dni}")
    public ResponseEntity<Void> deletePatientByDni(@PathVariable Long dni) {
        patientService.deletePatientByDni(dni);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public ResponseEntity<List<PatientModel>> getAllPatient() {
        List<PatientModel> patients = patientService.getAllPatient();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientModel> updatePatient(@PathVariable Long id, @RequestBody PatientModel patient) {
        PatientModel updatedPatient = patientService.updatePatient(id, patient);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }


    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllPatient() {
        patientService.deleteAllPatient();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

 */