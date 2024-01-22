package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.presentation.dto.AnalysisDTO;
import com.example.MiHistoriaClinica.persistence.model.Analysis;

import java.util.List;

public interface AnalysisService {
    Analysis createPatientAnalysis(Long medicId, String patientLinkCode, AnalysisDTO analysisDTO);

    List<Analysis> getAnalysisByPatientId(Long id);

    Analysis getAnalysisByAnalysisId(Long analysisId);

    void deletePatientAnalysis(String patientLinkCode, Long analysisId);

    List<Analysis> getAnalyzesByPatientLinkCode(String patientLinkCode);

    List<Analysis> getAnalysisByStatus(Long id, String status);

    List<String> getAllAnalysisNames();

    List<String> getAllMedicalCenters();

    String getAnalysisDescription(String analysisName);
}
