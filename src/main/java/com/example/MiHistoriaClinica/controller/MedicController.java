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


    /* TODO no se de que manera pedir el id del paciente para que llegue bien desde el front PREGUNTAR
    *   no se si se pueden pasar dos tokens distintos
    *   o si el medico puede elegir entre sus pacientes para levantar la historia clinica --
    *   tiene mas sentido que sea con el que esta en la consulta*/
    @PostMapping("/create-medical-history")
    public ResponseEntity<MedicalHistoryModel> createPatientMedicalHistory(@RequestHeader("Authorization") String token,
                                                                           @RequestHeader("patientLinkCode") String patientLinkCode,
                                                                           @RequestBody MedicalHistoryModelDTO medicalHistory)
                                                                           throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        MedicalHistoryModel createdMedicalHistory = medicService.createPatientMedicalHistory(medicId, patientLinkCode, medicalHistory);

        if (createdMedicalHistory == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(createdMedicalHistory, HttpStatus.CREATED);
        }
    }



    @GetMapping("/getById/{id}")
    public MedicModel getMedicById(@PathVariable Long id) {
        return medicService.getMedicById(id);
    }

    @GetMapping("/getByDni/{dni}")
    public MedicModel getMedicByDni(@PathVariable Long dni){
        return medicService.getMedicByDni(dni);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicModel>> getAllMedic() {
        List<MedicModel> medics = medicService.getAllMedic();
        return new ResponseEntity<>(medics, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
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


    /**
     * Este método recibe el código de enlace como parámetro de la solicitud y llama al método linkPatient() de la capa
     * de servicio para asociar al paciente correspondiente al médico.
     * Devuelve una respuesta vacía al cliente.
     */
    @PostMapping("/linkPatient")
    public ResponseEntity<Void> linkPatient(@RequestHeader("Authorization") String token, @RequestParam String linkCode) throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        medicService.linkPatient(linkCode, medicId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{medic-id}/get-patients")
    public ResponseEntity<List<PatientModel>> getPatients(@PathVariable("medic-id") Long parameter){
        List<PatientModel> patients = medicService.getPatientsByMedicId(parameter);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }


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

    @PostMapping("/createMedicalHistory")
  //  @PreAuthorize("hasRole('MEDIC_ROLE')")
    public MedicalHistoryModel createMedicalHistory(@RequestBody MedicalHistoryModel history){
        return medicService.createMedicalHistory(history);
    }


}
