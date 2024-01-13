package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.persistence.model.Medicine;
import com.example.MiHistoriaClinica.service.implementation.PatientServiceImpl;
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
@RequestMapping("/medicine")
@CrossOrigin("*")
public class MedicineController {

    private final PatientServiceImpl patientService;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);

    @Autowired
    public MedicineController(PatientServiceImpl patientService){
        this.patientService = patientService;
    }

    @GetMapping("/get-medicines")
    public ResponseEntity<List<Medicine>> getMedicines (@RequestHeader("Authorization") String token ) throws InvalidTokenException {
        List<Medicine> medicines = patientService.getMedicinesByPatientId(jwtValidator.getId(token));
        return new ResponseEntity<>(medicines, HttpStatus.OK);
    }

    @PutMapping("/update-medicine-status")
    public ResponseEntity<String> updateMedicineStatus(@RequestParam("medicineId") Long medicineId,
                                                       @RequestParam("status") String status) {
        Medicine medicine = patientService.getMedicineByMedicineId(medicineId);
        if (medicine == null )      return new ResponseEntity<>("Medicamento no encontrado", HttpStatus.NOT_FOUND);

        medicine.setStatus(status);
        patientService.saveMedicine(medicine);

        return ResponseEntity.ok("Estado del medicamento actualizado correctamente");
    }

    @GetMapping("/getMedicinesByStatus")
    public ResponseEntity<List<Medicine>> getMedicinesByStatus(@RequestHeader ("Authorization") String token,
                                                               @RequestParam("status") String status) throws InvalidTokenException {
        List<Medicine> filteredMedicines = patientService.getMedicinesByStatus(jwtValidator.getId(token), status);
        return new ResponseEntity<>(filteredMedicines, HttpStatus.OK);
    }
}
