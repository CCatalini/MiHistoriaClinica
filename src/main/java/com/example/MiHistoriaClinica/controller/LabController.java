package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.LabModel;
import com.example.MiHistoriaClinica.repository.LabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/laboratories")
public class LabController {

    @Autowired
    private LabRepository labRepository;

    @GetMapping
    public List<LabModel> getAllLaboratories() {
        return labRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabModel> getLaboratorioById(@PathVariable Long id) {
        LabModel laboratorio = labRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Laboratory not found" + id));

        if (laboratorio != null) {
            return ResponseEntity.ok(laboratorio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
/*
    @PostMapping
    public ResponseEntity<Laboratorio> createLaboratorio(@RequestBody Laboratorio laboratorio) {
        Laboratorio newLaboratorio = labRepository.createLaboratorio(laboratorio);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLaboratorio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Laboratorio> updateLaboratorio(@PathVariable Long id, @RequestBody Laboratorio laboratorio) {
        Laboratorio updatedLaboratorio = labRepository.updateLaboratorio(id, laboratorio);
        if (updatedLaboratorio != null) {
            return ResponseEntity.ok(updatedLaboratorio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaboratorio(@PathVariable Long id) {
        labRepository.deleteLaboratorio(id);
        return ResponseEntity.noContent().build();
    }
*/
}