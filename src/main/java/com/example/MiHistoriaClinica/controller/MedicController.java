package com.example.MiHistoriaClinica.controller;


import com.example.MiHistoriaClinica.model.*;
import com.example.MiHistoriaClinica.service.MedicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


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
    public MedicModel loginMedic(@RequestBody MedicModel medic) {
        return medicService.loginMedic(medic);
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
    public ArrayList<MedicModel> getAllMedic(){
        return medicService.getAllMedic();
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
    @PostMapping("/{medicId}/link-patient")
    public ResponseEntity<Void> linkPatient(@RequestParam String linkCode, @PathVariable Long medicId) {
        medicService.linkPatient(linkCode, medicId);
        return ResponseEntity.ok().build();
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
