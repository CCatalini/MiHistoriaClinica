package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.presentation.dto.AnalysisDTO;
import com.example.MiHistoriaClinica.persistence.model.Analysis;

import java.util.List;

public interface AnalysisService {
    Analysis createPatientAnalysis(Long medicId, String patientLinkCode, AnalysisDTO analysisDTO);

    List<Analysis> getAnalysisByPatientId(Long id);

    Analysis getAnalysisByAnalysisId(Long analysisId);

    void deletePatientAnalysis(String patientLinkCode, Long analysisId);
}
