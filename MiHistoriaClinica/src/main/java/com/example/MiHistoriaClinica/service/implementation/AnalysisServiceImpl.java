package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.presentation.dto.AnalysisDTO;
import com.example.MiHistoriaClinica.persistence.model.Analysis;
import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.repository.AnalysisRepository;
import com.example.MiHistoriaClinica.persistence.repository.CustomRepositoryAccess;
import com.example.MiHistoriaClinica.persistence.repository.MedicRepository;
import com.example.MiHistoriaClinica.persistence.repository.PatientRepository;
import com.example.MiHistoriaClinica.service.AnalysisService;
import com.example.MiHistoriaClinica.util.constant.AnalysisE;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final MedicRepository medicRepository;
    private final PatientRepository patientRepository;
    private final AnalysisRepository analysisRepository;
    private final CustomRepositoryAccess customRepositoryAccess;
    private final PatientServiceImpl patientService;

    public AnalysisServiceImpl(MedicRepository medicRepository, PatientRepository patientRepository, AnalysisRepository analysisRepository, CustomRepositoryAccess customRepositoryAccess, PatientServiceImpl patientService) {
        this.medicRepository = medicRepository;
        this.patientRepository = patientRepository;
        this.analysisRepository = analysisRepository;
        this.customRepositoryAccess = customRepositoryAccess;
        this.patientService = patientService;
    }

    public void saveAnalysis(Analysis analysis) {
        analysisRepository.save(analysis);
    }

    @Override
    public Analysis createPatientAnalysis(Long medicId, String patientLinkCode, AnalysisDTO analysisDTO) {

        Optional<Medic> medic = medicRepository.findById(medicId);
        Optional<Patient> patient = patientRepository.findByLinkCode(patientLinkCode);

        if (medic.isEmpty() || patient.isEmpty()) return null;
        else return customRepositoryAccess.createPatientAnalysis(analysisDTO, patient);

    }

    @Override
    public List<Analysis> getAnalysisByPatientId(Long id) {
        return analysisRepository.getAnalysisByPatientId(id);
    }

    @Override
    public Analysis getAnalysisByAnalysisId(Long analysisId) {
        Optional<Analysis> analysis = analysisRepository.findById(analysisId);
        return analysis.orElse(null);
    }

    @Override
    public void deletePatientAnalysis(String patientLinkCode, Long analysisId) {
        Optional<Patient> patientModel = patientRepository.findByLinkCode(patientLinkCode);

        if (patientModel.isPresent()){
            Patient thisPatient = patientModel.get();
            List<Analysis> analysisList = thisPatient.getAnalysis();

            Analysis analysisToDelete = analysisList.stream()
                                                        .filter(analysis -> analysis.getAnalysis_id().equals(analysisId))
                                                        .findFirst()
                                                        .orElse(null);
            if (analysisToDelete != null){
                analysisList.remove(analysisToDelete);
                savePatient(thisPatient);
            }
        }

    }

    @Override
    public List<Analysis> getAnalyzesByPatientLinkCode(String patientLinkCode) {
        Optional<Patient> patientModel = patientRepository.findByLinkCode(patientLinkCode);

        return patientModel.get().getAnalysis();
    }


    private void savePatient(Patient thisPatient) {
        patientRepository.save(thisPatient);
    }

    @Override
    public List<Analysis> getAnalysisByStatus(Long id, String status) {
        return patientRepository.getAnalysisByPatientIdAndStatus(id, status);
    }

    @Override
    public List<String> getAllAnalysisNames() {
        return AnalysisE.getNames();
    }

    @Override
    public List<String> getAllMedicalCenters() {
        return MedicalCenterE.getAllMedicalCenters();
    }
}
