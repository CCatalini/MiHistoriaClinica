package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.AnalysisModel;
import com.example.MiHistoriaClinica.model.MedicineModel;
import com.example.MiHistoriaClinica.repository.AnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analysis")
@CrossOrigin("*")
public class AnalysisController {

    @Autowired private AnalysisRepository analysisRepository;

    @PostMapping("/addAnalysis")
    public AnalysisModel addAnalysis(@RequestBody AnalysisModel analysis){
        return analysisRepository.save(analysis);
    }

    @PutMapping("/update")
    public AnalysisModel updateAnalysis(@PathVariable Long id, @RequestBody AnalysisModel analysis){
        AnalysisModel analysisModel = analysisRepository.findById(id).orElseThrow(()
        -> new ResourceNotFoundException("Analysis not found"));

    analysisModel.setName(analysis.getName());
    analysisModel.setDescription(analysis.getDescription());
    analysisModel.setMedicalCenter(analysis.getMedicalCenter());

    return analysisRepository.save(analysisModel);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Long id){
        analysisRepository.deleteById(id);
    }

    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name){
        analysisRepository.deleteByName(name);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        analysisRepository.deleteAll();
    }

    // todo Cami verificar si funciona con postman
    @GetMapping("/findAllAnalysis")
    public List<AnalysisModel> findAllAnalysis() {
        return analysisRepository.findAll();
    }


    /**
 * metodos ABM
 */


}
