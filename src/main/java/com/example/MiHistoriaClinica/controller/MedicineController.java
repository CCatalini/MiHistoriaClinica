package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.MedicineModel;
import com.example.MiHistoriaClinica.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicines")
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @GetMapping("/findAllMedicine")
    public List<MedicineModel> findAllMedicine () {
        return medicineRepository.findAll();
    }

    @GetMapping("/findAllById/{id}")
    public MedicineModel getMedicineById (@PathVariable Long id){
        return medicineRepository.findById(id).orElse(null);
    }

    @PostMapping("/saveNewMedicine")
    public MedicineModel addMedicine(@RequestBody MedicineModel medicine) {
        return medicineRepository.save(medicine);
    }

    @PutMapping("/updateMedicine/{id}")
    public MedicineModel updateMedicine(@PathVariable Long id, @RequestBody MedicineModel medicine) {
        MedicineModel newMedicine = medicineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicina not found" + id));

        newMedicine.setMedicineName(medicine.getMedicineName());
        newMedicine.setActiveIngredient(medicine.getActiveIngredient());
        newMedicine.setDescription(medicine.getDescription());
        newMedicine.setLab(medicine.getLab());

        return newMedicine;
    }

    @DeleteMapping("/deleteMedicine/{id}")
    public ResponseEntity<Object> deleteMedicine(@PathVariable Long id){
        MedicineModel medicine = medicineRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Medicine not found"));
        medicineRepository.delete(medicine);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllMedicines(){
        medicineRepository.deleteAll();
    }

}

