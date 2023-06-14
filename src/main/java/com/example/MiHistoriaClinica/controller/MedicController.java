package com.example.MiHistoriaClinica.controller;


import com.example.MiHistoriaClinica.dto.*;
import com.example.MiHistoriaClinica.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.model.*;
import com.example.MiHistoriaClinica.service.MedicServiceImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<MedicModel> createMedic (@RequestBody MedicSignupDTO medicDTO) {

       MedicModel createdMedic = medicService.createMedic(medicDTO);
       return new ResponseEntity<>(createdMedic, HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenDTO> loginMedic (@RequestBody MedicLoginDTO medicDto){
        MedicModel loggedInMedic = medicService.loginMedic(medicDto);
        TokenDTO token = jwt.generateToken(loggedInMedic.getMedicId(), "MEDIC");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutMedic(@RequestHeader("Authorization") String token){
        return jwt.invalidateToken(token);
    }


    /**                         MÉTODOS DEL MEDICO EN RELACIÓN AL PACIENTE                                                  */

    @PostMapping("/linkPatient")
    public ResponseEntity<Void> linkPatient(@RequestHeader("Authorization") String token, @RequestParam String linkCode) throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        medicService.linkPatient(linkCode, medicId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/get-patients")
    public ResponseEntity<List<PatientModel>> getPatients(@RequestHeader("Authorization") String token) throws InvalidTokenException {

        Long medicId = jwtValidator.getId(token);

        List<PatientModel> patients = medicService.getPatientsByMedicId(medicId);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }


    @PostMapping("/create-medical-history")
    public ResponseEntity<MedicalHistoryModel> createPatientMedicalHistory(@RequestHeader("Authorization") String token,
                                                                           @RequestHeader("patientLinkCode") String patientLinkCode,
                                                                           @RequestBody MedicalHistoryModelDTO medicalHistory)
                                                                           throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        MedicalHistoryModel createdMedicalHistory = medicService.createPatientMedicalHistory(medicId, patientLinkCode, medicalHistory);

        if (createdMedicalHistory == null)      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else                                    return new ResponseEntity<>(createdMedicalHistory, HttpStatus.CREATED);

    }


    @PostMapping("/create-medicine")
    public ResponseEntity<MedicineModel> createPatientMedicine (@RequestHeader("Authorization") String token,
                                                                @RequestHeader("patientLinkCode") String patientLinkCode,
                                                                @RequestBody MedicineDTO medicine)
                                                                throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        MedicineModel createdMedicine = medicService.createPatientMedicine(medicId, patientLinkCode, medicine);

        if(createdMedicine == null)         return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else                                return new ResponseEntity<>(createdMedicine, HttpStatus.CREATED);
    }


    @GetMapping("/get-patient-medicines")
    public ResponseEntity<List<MedicineModel>> getPatientMedicines (@RequestHeader("patientLinkCode") String patientLinkCode) throws InvalidTokenException {
        List<MedicineModel> medicines = medicService.getMedicinesByPatientLinkCode(patientLinkCode);
        if (medicines==null)    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(medicines, HttpStatus.OK);
    }


    /* TODO --> para updateMedicine se necesita la lista de medicamentos del paciente (que se obtiene con el linkcode )
                y el id del medicamento que queremos modificar
                yo tambien necesito el cuerpo de la medicina nueva
     */





    @PostMapping("/addMedicine")
  //  @PreAuthorize("hasRole('MEDIC_ROLE')")
    public MedicineModel addMedicine(@RequestBody MedicineModel medicine) {
        return medicService.addMedicine(medicine);
    }

    @PostMapping("/addAnalysis")
   // @PreAuthorize("hasRole('MEDIC_ROLE')")
    public AnalysisModel addAnalysis(@RequestBody AnalysisModel analysis){
        return medicService.addAnalysis(analysis);
    }




}

/*
 * METODOS QUE NO SE USAN CREO
 *
 *     @GetMapping("/getById/{id}")
 *     public MedicModel getMedicById(@PathVariable Long id) {
 *         return medicService.getMedicById(id);
 *     }
 *
 *     @GetMapping("/getByDni/{dni}")
 *     public MedicModel getMedicByDni(@PathVariable Long dni){
 *         return medicService.getMedicByDni(dni);
 *     }
 *
 *

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicModel>> getAllMedic() {
        List<MedicModel> medics = medicService.getAllMedic();
        return new ResponseEntity<>(medics, HttpStatus.OK);
    }
 *
 *
 *     @PutMapping("/update/{id}")
    public MedicModel updateMedic(@PathVariable Long id, @RequestBody MedicModel newMedic) {
       return medicService.updateMedic(id, newMedic);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedic(@PathVariable Long id) {
        return medicService.deleteMedic(id);
    }

    @DeleteMapping("/deleteByDni/{dni}")
    public ResponseEntity<Void> deleteMedicByDni (@PathVariable Long dni){
        return medicService.deleteMedicByDni(dni);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllMedic(){
        medicService.deleteAllMedic();
    }


 *
 */