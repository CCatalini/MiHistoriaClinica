package com.example.MiHistoriaClinica.service.interfaces;

import com.example.MiHistoriaClinica.dto.AnalysisDTO;
import com.example.MiHistoriaClinica.model.AnalysisModel;

import java.util.List;

public interface AnalysisService {
    AnalysisModel createPatientAnalysis(Long medicId, String patientLinkCode, AnalysisDTO analysisDTO);

    List<AnalysisModel> getAnalysisByPatientId(Long id);
}
