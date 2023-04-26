package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.MedicineModel;
import com.example.MiHistoriaClinica.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicine")
@CrossOrigin("*")
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @GetMapping("/findAllMedicine")
    public List<MedicineModel> findAllMedicine () {
        return medicineRepository.findAll();
    }

    @GetMapping("/findById/{id}")
    public MedicineModel getMedicineById (@PathVariable Long id){
        return medicineRepository.findById(id).orElse(null);
    }

    @GetMapping("/findByMedicineNameAndLab")
    public Object getMedicineByNameAndLab(@PathVariable String medicineName, @PathVariable String lab){
        MedicineModel medicine = medicineRepository.findByMedicineNameAndLab(medicineName, lab);
        if(medicine == null) return new ResourceNotFoundException("Medicina no encontrada");
        else                 return medicine;
    }


    @PutMapping("/updateMedicine/{id}")
    public MedicineModel updateMedicine(@PathVariable Long id, @RequestBody MedicineModel medicine) {
        MedicineModel newMedicine = medicineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicina not found" + id));

        newMedicine.setMedicineName(medicine.getMedicineName());
        newMedicine.setActiveIngredient(medicine.getActiveIngredient());
        newMedicine.setDescription(medicine.getDescription());

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

