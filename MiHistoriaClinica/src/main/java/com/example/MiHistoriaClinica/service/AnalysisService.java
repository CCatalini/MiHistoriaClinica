package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.presentation.dto.AnalysisDTO;
import com.example.MiHistoriaClinica.persistence.model.AnalysisModel;

import java.util.List;

public interface AnalysisService {
    AnalysisModel createPatientAnalysis(Long medicId, String patientLinkCode, AnalysisDTO analysisDTO);

    List<AnalysisModel> getAnalysisByPatientId(Long id);

    AnalysisModel getAnalysisByAnalysisId(Long analysisId);

    void deletePatientAnalysis(String patientLinkCode, Long analysisId);
}
