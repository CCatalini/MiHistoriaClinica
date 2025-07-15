package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.presentation.dto.MedicalFileDTO;
import com.example.MiHistoriaClinica.presentation.dto.PatientLoginDTO;
import com.example.MiHistoriaClinica.presentation.dto.PatientDTO;
import com.example.MiHistoriaClinica.presentation.dto.TurnoDTO;
import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import com.example.MiHistoriaClinica.util.exception.PatientNotFoundException;
import com.example.MiHistoriaClinica.util.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.Medicine;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.model.Turnos;
import com.example.MiHistoriaClinica.persistence.repository.*;
import com.example.MiHistoriaClinica.service.PatientService;
import com.example.MiHistoriaClinica.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.example.MiHistoriaClinica.presentation.dto.MedicTurnosDTO;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final CustomRepositoryAccess customRepositoryAccess;
    private final MedicineRepository medicineRepository;
    private final MedicRepository medicRepository;
    private final TurnosRepository turnosRepository;
    
    @Autowired
    private EmailService emailService;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, CustomRepositoryAccess customRepositoryAccess, MedicineRepository medicineRepository, MedicRepository medicRepository, TurnosRepository turnosRepository) {
        this.patientRepository = patientRepository;
        this.customRepositoryAccess = customRepositoryAccess;
        this.medicineRepository = medicineRepository;
        this.medicRepository = medicRepository;
        this.turnosRepository = turnosRepository;
    }

    /**
     * Método para generar el código de enlace y actualizar el registro del paciente con el nuevo código
     * @return linkCode
     */
    public String generateLinkCode(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            throw new RuntimeException("No se pudo generar el código de enlace. El paciente no existe.");
        }
        String linkCode = UUID.randomUUID().toString().substring(0, 4); // Generar un código aleatorio de 4 caracteres
        patient.setLinkCode(linkCode);
        patientRepository.save(patient);
        return linkCode;
    }

    @Override
    public Patient createPatient(PatientDTO patient) {
        return customRepositoryAccess.saveDTO(patient);
    }

    @Override
    public Patient loginPatient(PatientLoginDTO patient) {
        Patient result = patientRepository.findByDniAndPassword(patient.getDni(), patient.getPassword());
        if (result == null) {
            throw new PatientNotFoundException();
        } else {
            return result;
        }
    }

    @Override
    public List<Medic> getMedicsByPatientId(Long id) {
        return patientRepository.getMedicsByPatientId(id);
    }

    @Override
    public List<Medicine> getMedicinesByPatientId(Long id) {
        return patientRepository.getMedicinesByPatientId(id);
    }

    public List<Medicine> getMedicinesByStatus(Long id, String status) {
        return patientRepository.getMedicinesByPatientIdAndStatus(id, status);
    }

    @Override
    public Patient updatePatient(Long id, Patient newPatient) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        patient.setName(newPatient.getName());
        patient.setLastname(newPatient.getLastname());
        patient.setEmail(newPatient.getEmail());
        patient.setPassword(newPatient.getPassword());
        patient.setDni(newPatient.getDni());
        patient.setBirthdate(newPatient.getBirthdate());

        return patientRepository.save(patient);
    }

    @Override
    public MedicalFileDTO getMedicalHistory(Long id){
        Patient thisPatient = patientRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return new MedicalFileDTO(thisPatient.getMedicalFile());
    }

    @Override
    public Medicine getMedicineByMedicineId(Long medicineId) {
        Optional<Medicine> medicine = medicineRepository.findById(medicineId);
        return medicine.orElse(null);
    }

    @Override
    public void saveMedicine(Medicine medicine) {
        medicineRepository.save(medicine);
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }

    @Override
    public Patient getPatientByDni(Long dni) {
        Patient patient = patientRepository.findByDni(dni);
        if (patient == null) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        } else {
            return patient;
        }
    }

    @Override
    public PatientDTO getPatientInfo(Long patientId) {
        ModelMapper modelMapper = new ModelMapper();
        Patient patient = getPatientById(patientId);
        return modelMapper.map(patient, PatientDTO.class);
    }

    @Override
    public void reserveTurno(Long patientId, Long turnoId) {
        Turnos turno = turnosRepository.findById(turnoId)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));

        if (!turno.isAvailable()) {
            throw new RuntimeException("Turno no disponible");
        }

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        turno.setPatient(patient);
        turno.setAvailable(false);

        turnosRepository.save(turno);
        
        // Enviar email de confirmación
        try {
            emailService.sendTurnoConfirmationEmail(patient, turno);
        } catch (Exception e) {
            // Log error pero no fallar la operación
            System.err.println("Error enviando email de confirmación: " + e.getMessage());
        }
    }

    @Override
    public List<Turnos> getMisTurnos(Long id) {
        Patient patient = patientRepository.findById(id).get();
        return turnosRepository.findByPatient(patient);
    }

    @Override
    public void deleteTurno(Long id) {
        turnosRepository.deleteById(id);
    }

    @Override
    public List<Medic> getMedicsBySpecialty(Long id, String specialty) {
        MedicalSpecialtyE medicalSpecialtyE = MedicalSpecialtyE.getEnumFromName(specialty);
        return patientRepository.getMedicsBySpecialty(id, medicalSpecialtyE);
    }

    @Override
    public List<Turnos> getAvailableTurnosByMedic(Long medicId) {
        return turnosRepository.findByMedic_MedicIdAndAvailableTrue(medicId);
    }

    @Override
    public List<MedicTurnosDTO> searchAvailableTurnosBySpecialtyAndDate(String specialty, String dateIso) {
        java.time.LocalDate date = java.time.LocalDate.parse(dateIso);
        com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE specEnum = com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE.getEnumFromName(specialty);
        List<Turnos> turnos = turnosRepository.findByMedicSpecialtyAndFechaTurnoAndAvailableTrue(specEnum, date);

        java.util.Map<Long, MedicTurnosDTO> map = new java.util.HashMap<>();

        for (Turnos t : turnos) {
            MedicTurnosDTO dto = map.computeIfAbsent(
                    t.getMedic().getMedicId(),
                    id -> new MedicTurnosDTO(id, t.getMedicFullName(), t.getMedicSpecialty(), t.getMedicalCenter().getName(), new java.util.ArrayList<>())
            );
            dto.getAvailableTimes().add(t.getHoraTurno());
        }

        return new java.util.ArrayList<>(map.values());
    }


}


