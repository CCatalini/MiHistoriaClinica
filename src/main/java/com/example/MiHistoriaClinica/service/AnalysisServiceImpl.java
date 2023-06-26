package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.dto.AnalysisDTO;
import com.example.MiHistoriaClinica.model.AnalysisModel;
import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.MedicineModel;
import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.repository.AnalysisRepository;
import com.example.MiHistoriaClinica.repository.CustomRepositoryAccess;
import com.example.MiHistoriaClinica.repository.MedicRepository;
import com.example.MiHistoriaClinica.repository.PatientRepository;
import com.example.MiHistoriaClinica.service.interfaces.AnalysisService;
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

    public void saveAnalysis(AnalysisModel analysis) {
        analysisRepository.save(analysis);
    }

    @Override
    public AnalysisModel createPatientAnalysis(Long medicId, String patientLinkCode, AnalysisDTO analysisDTO) {

        Optional<MedicModel> medic = medicRepository.findById(medicId);
        Optional<PatientModel> patient = patientRepository.findByLinkCode(patientLinkCode);

        if (medic.isEmpty() || patient.isEmpty()) return null;
        else return customRepositoryAccess.createPatientAnalysis(analysisDTO, patient);

    }

    @Override
    public List<AnalysisModel> getAnalysisByPatientId(Long id) {
        return analysisRepository.getAnalysisByPatientId(id);
    }

    @Override
    public AnalysisModel getAnalysisByAnalysisId(Long analysisId) {
        Optional<AnalysisModel> analysis = analysisRepository.findById(analysisId);
        return analysis.orElse(null);
    }

    @Override
    public void deletePatientAnalysis(String patientLinkCode, Long analysisId) {
        Optional<PatientModel> patientModel = patientRepository.findByLinkCode(patientLinkCode);

        if (patientModel.isPresent()){
            PatientModel thisPatient = patientModel.get();
            List<AnalysisModel> analysisList = thisPatient.getAnalysis();

            AnalysisModel analysisToDelete = analysisList.stream()
                                                        .filter(analysis -> analysis.getAnalysis_id().equals(analysisId))
                                                        .findFirst()
                                                        .orElse(null);
            if (analysisToDelete != null){
                analysisList.remove(analysisToDelete);
                savePatient(thisPatient);
            }
        }

    }

    public List<AnalysisModel> getAnalyzesByPatientLinkCode(String patientLinkCode) {
        Optional<PatientModel> patientModel = patientRepository.findByLinkCode(patientLinkCode);

        return patientModel.get().getAnalysis();
    }







    private void savePatient(PatientModel thisPatient) {
        patientRepository.save(thisPatient);
    }



}
