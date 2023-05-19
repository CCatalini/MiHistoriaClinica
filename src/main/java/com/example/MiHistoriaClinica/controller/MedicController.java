package com.example.MiHistoriaClinica.controller;


import com.example.MiHistoriaClinica.dto.TokenDTO;
import com.example.MiHistoriaClinica.model.*;
import com.example.MiHistoriaClinica.service.MedicServiceImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/medic")
@CrossOrigin("*")
public class MedicController {

    private final MedicServiceImpl medicService;

    @Autowired
    public MedicController(MedicServiceImpl medicService) {
        this.medicService = medicService;
    }


    @PostMapping("/signup")
    public MedicModel createMedic(@RequestBody MedicModel medic) {
        return medicService.createMedic(medic);
    }



    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenDTO> loginMedic(@RequestBody MedicModel medic) {
        MedicModel loggedMedic = medicService.loginMedic(medic);
        JwtGenerator jwt = new JwtGeneratorImpl();
        TokenDTO token = jwt.generateToken(loggedMedic.getMedic_id().toString(),"MEDIC");
        return new ResponseEntity<>(token, HttpStatus.OK);
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
    @PostMapping("/link-patient")
    public ResponseEntity<Void> linkPatient(@RequestParam String linkCode, @RequestParam String dni, @RequestHeader("Authorization") String token) {
        JwtValidator validator = new JwtValidatorImpl(new JwtGeneratorImpl());
        String medicId = validator.validateMedic(token);
        medicService.linkPatient(linkCode, dni, Long.parseLong(medicId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addMedicine")
  //  @PreAuthorize("hasRole('MEDIC_ROLE')")
    public MedicineModel addMedicine(@RequestBody MedicineModel medicine, @RequestHeader("Authorization") String token){
        JwtValidator validator = new JwtValidatorImpl(new JwtGeneratorImpl());
        String id = validator.validateMedic(token);
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
