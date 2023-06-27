package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.dto.MedicalAppointmentDTO;
import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.MedicalAppointmentModel;
import com.example.MiHistoriaClinica.model.PatientModel;
import com.example.MiHistoriaClinica.repository.CustomRepositoryAccess;
import com.example.MiHistoriaClinica.repository.MedicRepository;
import com.example.MiHistoriaClinica.repository.PatientRepository;
import com.example.MiHistoriaClinica.service.interfaces.MedicalAppointmentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalAppointmentServiceImpl implements MedicalAppointmentService {

    private final MedicRepository medicRepository;
    private final PatientRepository patientRepository;
    private final CustomRepositoryAccess customRepositoryAccess;


    public MedicalAppointmentServiceImpl(MedicRepository medicRepository, PatientRepository patientRepository, CustomRepositoryAccess customRepositoryAccess) {
        this.medicRepository = medicRepository;
        this.patientRepository = patientRepository;
        this.customRepositoryAccess = customRepositoryAccess;
    }


    public MedicalAppointmentModel createMedicalAppointment(Long medicId, String patientLinkCode, MedicalAppointmentDTO appointmentDTO) {
        Optional<MedicModel> medic = medicRepository.findById(medicId);
        Optional<PatientModel> patient = patientRepository.findByLinkCode(patientLinkCode);

        if (medic.isEmpty() || patient.isEmpty()) return null;
        else return customRepositoryAccess.createMedicalAppointment(appointmentDTO, patient);

    }
}
