package com.example.MiHistoriaClinica.controller;


import com.example.MiHistoriaClinica.exception.MedicNotFoundException;
import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.*;
import com.example.MiHistoriaClinica.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/medic")
@CrossOrigin("*")
public class MedicController {

    @Autowired  private MedicRepository medicRepository;
    @Autowired private MedicineRepository medicineRepository;

    @Autowired private AnalysisRepository analysisRepository;
    @Autowired private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired private RoleRepository roleRepository;

    @PostMapping("/signup")
    public MedicModel createMedic(@RequestBody MedicModel medic) {

        // Asignar rol por defecto al médico
        Role role = roleRepository.findByName("MEDIC_ROLE");
        medic.setRole(role);

        return medicRepository.save(medic);
    }


    /**
     * En vez de devolver un objeto medico, devolves un objeto que es {token: (token generado por el jwt)}
     * Rochi va a guardar ese token en el local storage y lo va a usar para hacer las peticiones a los endpoints
     * Rochi lo deberia mandar como "Bearer token"
     * **/
    @PostMapping("/login")
    @ResponseBody
    public MedicModel loginMedic(@RequestBody MedicModel medic) {
        MedicModel result = medicRepository.findByMatriculaAndPassword(medic.getMatricula(), medic.getPassword());
        if (result == null) {
            throw new MedicNotFoundException();
        } else {
            return result;
        }
    }

    @GetMapping("/getById/{id}")
    public MedicModel getMedicById(@PathVariable Long id) {
        return medicRepository.findById(id).orElse(null);
    }

    @GetMapping("/getByDni/{id}")
    public MedicModel getMedicByDni(@PathVariable Long dni){
        return medicRepository.findByDni(dni);
    }
    @GetMapping("/getAll")
    public ArrayList<MedicModel> getAllMedic(){
        return (ArrayList<MedicModel>) medicRepository.findAll();
    }

    @PutMapping("/update/{id}")
    public MedicModel updateMedic(@PathVariable Long id, @RequestBody MedicModel newMedic) {
        MedicModel medic = medicRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Medic not found"));

        medic.setName(newMedic.getName());
        medic.setLastname(newMedic.getLastname());
        medic.setDni(newMedic.getDni());
        medic.setEmail(newMedic.getEmail());
        medic.setMatricula(newMedic.getMatricula());
        medic.setSpecialty(newMedic.getSpecialty());
        medic.setPassword(newMedic.getPassword());

        return medicRepository.save(medic);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedic(@PathVariable Long id) {
        MedicModel medic = medicRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Doctor not found"));
        medicRepository.delete(medic);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteByDni/{dni}")
    public void deleteMedicByDni (@PathVariable Long dni){
        medicRepository.deleteByDni(dni);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllMedic(){
        medicRepository.deleteAll();
    }





    @PostMapping("/addMedicine")
  //  @PreAuthorize("hasRole('MEDIC_ROLE')")
    public MedicineModel addMedicine(@RequestBody MedicineModel medicine) {
        return medicineRepository.save(medicine);
    }


    @PostMapping("/addAnalysis")
   // @PreAuthorize("hasRole('MEDIC_ROLE')")
    public AnalysisModel addAnalysis(@RequestBody AnalysisModel analysis){
        return analysisRepository.save(analysis);
    }



    @PostMapping("/createMedicalHistory")
  //  @PreAuthorize("hasRole('MEDIC_ROLE')")
    public MedicalHistoryModel createMedicalHistory(@RequestBody MedicalHistoryModel history){
        return medicalHistoryRepository.save(history);
    }


}
