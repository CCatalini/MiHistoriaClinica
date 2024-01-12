package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.presentation.dto.MedicalAppointmentDTO;
import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.MedicalAppointment;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.repository.CustomRepositoryAccess;
import com.example.MiHistoriaClinica.persistence.repository.MedicRepository;
import com.example.MiHistoriaClinica.persistence.repository.PatientRepository;
import com.example.MiHistoriaClinica.service.MedicalAppointmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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


    public MedicalAppointment createMedicalAppointment(Long medicId, String patientLinkCode, MedicalAppointmentDTO appointmentDTO) {
        Optional<Medic> medic = medicRepository.findById(medicId);
        Optional<Patient> patient = patientRepository.findByLinkCode(patientLinkCode);

        if (medic.isEmpty() || patient.isEmpty()) return null;
        else return customRepositoryAccess.createMedicalAppointment(appointmentDTO, patient, medic);

    }


    public List<MedicalAppointment> getAppointmentListByLinkCode(String patientLinkCode) {
        Optional<Patient> patient = patientRepository.findByLinkCode(patientLinkCode);
        if (patient.isPresent())        return patient.get().getMedicalAppointments();
        else                            return new ArrayList<>(); // Devuelve una lista vacía o null según corresponda

    }


    public List<MedicalAppointment> getAppointmentListById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent())        return patient.get().getMedicalAppointments();
        else                            return new ArrayList<>();

    }
}
