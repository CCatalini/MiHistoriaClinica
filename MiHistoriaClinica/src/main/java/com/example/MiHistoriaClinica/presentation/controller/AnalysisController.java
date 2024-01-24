package com.example.MiHistoriaClinica.presentation.controller;

import com.example.MiHistoriaClinica.presentation.dto.AnalysisDTO;
import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.persistence.model.Analysis;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.repository.PatientRepository;
import com.example.MiHistoriaClinica.service.implementation.AnalysisServiceImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/analysis")
@CrossOrigin("*")
public class AnalysisController {

    private final AnalysisServiceImpl analysisService;
    private final PatientRepository aux;
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);

    @Autowired
    public AnalysisController(AnalysisServiceImpl analysisService, PatientRepository patientRepository) {
        this.analysisService = analysisService;
        this.aux = patientRepository;
    }

    @GetMapping("/all-names")
    public ResponseEntity<List<String>> getAllAnalysisNames(){
        List<String> analysisNames = analysisService.getAllAnalysisNames();
        return new ResponseEntity<>(analysisNames, HttpStatus.OK);
    }

    @GetMapping("/medicalCenter/all-names")
    public ResponseEntity<List<String>> getAllMedicalCenters() {
        List<String> analysisNames = analysisService.getAllMedicalCenters();
        return new ResponseEntity<>(analysisNames, HttpStatus.OK);

    }

    @GetMapping("/description")
    public ResponseEntity<String> getAnalysisDescription(@RequestParam("analysisName") String analysisName) {
        String description = analysisService.getAnalysisDescription(analysisName);
        return new ResponseEntity<>(description, HttpStatus.OK);
    }


    @PostMapping("/medic/create-patient-analysis")
    public ResponseEntity<Analysis> createPatientAnalysis(@RequestHeader("Authorization") String token,
                                                          @RequestHeader("patientLinkCode") String patientLinkCode,
                                                          @RequestBody AnalysisDTO analysisDTO)
                                                          throws InvalidTokenException {
        Long medicId = jwtValidator.getId(token);
        Analysis createdAnalysis = analysisService.createPatientAnalysis(medicId, patientLinkCode, analysisDTO);

        if (createdAnalysis == null)      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else                                    return new ResponseEntity<>(createdAnalysis, HttpStatus.CREATED);

    }

    @GetMapping("/medic/get-analysis")
    public ResponseEntity<List<Analysis>> getPatientAnalysisList(@RequestHeader("patientLinkCode") String patientLinkCode) {
        List<Analysis> analysisList = analysisService.getAnalyzesByPatientLinkCode(patientLinkCode);
        if (analysisList == null)       return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return                                 new ResponseEntity<>(analysisList, HttpStatus.OK);
    }

    @DeleteMapping("/medic/delete-analysis")
    public ResponseEntity<Void> deletePatientAnalysis(@RequestHeader("patientLinkCode") String patientLinkCode,
                                                      @RequestParam("analysisId") Long analysisId){
        analysisService.deletePatientAnalysis(patientLinkCode, analysisId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/medic/get-analysis-byStatus")
    public ResponseEntity<List<Analysis>> getPatientAnalysisByStatus(@RequestHeader("patientLinkCode") String patientLinkCode,
                                                                     @RequestParam("status") String status){
        Optional<Patient> patientModel = aux.findByLinkCode(patientLinkCode);
        List<Analysis> filteredAnalysis = analysisService.getAnalysisByStatus(patientModel.get().getPatientId(), status);
        return new ResponseEntity<>(filteredAnalysis, HttpStatus.OK);
    }



    @GetMapping("/patient/get-analysis")
    public ResponseEntity<List<Analysis>> getMyAnalysisList(@RequestHeader("Authorization") String token) throws InvalidTokenException {
        List<Analysis> analysisList = analysisService.getAnalysisByPatientId(jwtValidator.getId(token));
        return new ResponseEntity<>(analysisList, HttpStatus.OK);
    }

    @PutMapping("/patient/update-analysis-status")
    public ResponseEntity<String> updateAnalysisStatus(@RequestHeader("analysis_id") Long analysisId,
                                                       @RequestParam("status") String status){
        Analysis analysis = analysisService.getAnalysisByAnalysisId(analysisId);
        if (analysis == null)       return new ResponseEntity<>("AnalysisE no disponible", HttpStatus.NOT_FOUND);

        analysis.setStatus(status);
        analysisService.saveAnalysis(analysis);

        return ResponseEntity.ok("Estado actualizado");
    }

    @GetMapping("/patient/get-analysis-byStatus")
    public ResponseEntity<List<Analysis>> getAnalysisByStatus(@RequestHeader("Authorization") String token,
                                                              @RequestParam("status") String status) throws InvalidTokenException {
        List<Analysis> filteredAnalysis = analysisService.getAnalysisByStatus(jwtValidator.getId(token), status);
        return new ResponseEntity<>(filteredAnalysis, HttpStatus.OK);
    }

    @GetMapping("/patient/get-analysis-by-medicalCenter")
    public ResponseEntity<List<Analysis>> getAnalysisByMedicalCenter(@RequestHeader("Authorization") String token,
                                                                     @RequestParam("medicalCenter") String medicalCenter) throws InvalidTokenException {
        List<Analysis> filteredAnalysis = analysisService.getAnalysisByMedicalCenter(jwtValidator.getId(token), medicalCenter);
        return new ResponseEntity<>(filteredAnalysis, HttpStatus.OK);
    }

}
