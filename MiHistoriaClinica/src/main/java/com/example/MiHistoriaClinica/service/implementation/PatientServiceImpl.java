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
import java.util.Random;
import java.util.UUID;
import com.example.MiHistoriaClinica.presentation.dto.MedicTurnosDTO;
import com.example.MiHistoriaClinica.presentation.dto.TurnoDisponibleDTO;
import com.example.MiHistoriaClinica.service.EmailService;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final CustomRepositoryAccess customRepositoryAccess;
    private final MedicineRepository medicineRepository;
    private final MedicRepository medicRepository;
    private final TurnosRepository turnosRepository;
    private final EmailService emailService;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, CustomRepositoryAccess customRepositoryAccess, MedicineRepository medicineRepository, MedicRepository medicRepository, TurnosRepository turnosRepository, EmailService emailService) {
        this.patientRepository = patientRepository;
        this.customRepositoryAccess = customRepositoryAccess;
        this.medicineRepository = medicineRepository;
        this.medicRepository = medicRepository;
        this.turnosRepository = turnosRepository;
        this.emailService = emailService;
    }

    /**
     * Method para generar el código de enlace y actualizar el registro del paciente con el nuevo código
     *
     * @return linkCode
     */
    public String generateLinkCode(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            throw new RuntimeException("No se pudo generar el código de enlace. El paciente no existe.");
        }

        // código numérico de 4 dígitos
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        String linkCode = String.valueOf(code);

        patient.setLinkCode(linkCode);
        patientRepository.save(patient);
        return linkCode;
    }

    @Override
    public Patient createPatient(PatientDTO patient) {
        // Crear el paciente
        Patient newPatient = customRepositoryAccess.saveDTO(patient);

        // Generar token de verificación
        String verificationToken = UUID.randomUUID().toString();
        newPatient.setVerificationToken(verificationToken);
        newPatient.setEmailVerified(false);
        newPatient.setEnabled(false);

        // Guardar con el token
        newPatient = patientRepository.save(newPatient);

        // Enviar email de verificación
        String verificationUrl = "http://localhost:4200/patient/verify?token=" + verificationToken;
        try {
            emailService.sendVerificationEmail(newPatient, verificationUrl);
        } catch (Exception e) {
            // Log pero no fallar el registro
            System.err.println("Error enviando email de verificación: " + e.getMessage());
        }

        return newPatient;
    }

    public Patient verifyEmail(String token) {
        Patient patient = patientRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Token de verificación inválido o expirado"));

        if (patient.isEmailVerified()) {
            throw new RuntimeException("El email ya ha sido verificado");
        }

        patient.setEmailVerified(true);
        patient.setEnabled(true);
        patient.setVerificationToken(null);

        patient = patientRepository.save(patient);

        // Enviar email de bienvenida
        try {
            emailService.sendWelcomeEmail(patient);
        } catch (Exception e) {
            System.err.println("Error enviando email de bienvenida: " + e.getMessage());
        }

        return patient;
    }

    @Override
    public Patient loginPatient(PatientLoginDTO patient) {
        Patient result = patientRepository.findByDniAndPassword(patient.getDni(), patient.getPassword());
        if (result == null) {
            throw new PatientNotFoundException();
        }

        // Verificar que el email esté verificado
        if (!result.isEmailVerified()) {
            throw new RuntimeException("Debes verificar tu email antes de iniciar sesión. Revisa tu correo electrónico.");
        }

        if (!result.isEnabled()) {
            throw new RuntimeException("Tu cuenta no está activa. Por favor contacta al soporte.");
        }

        return result;
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
    public MedicalFileDTO getMedicalHistory(Long id) {
        Patient thisPatient = patientRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        
        // Si el paciente no tiene historia clínica, retornar un DTO vacío
        if (thisPatient.getMedicalFile() == null) {
            return new MedicalFileDTO();
        }
        
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

    public void createTurno(Long patientId, Long medicId, TurnoDTO request, String medicalCenter) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
        Medic medic = medicRepository.findById(medicId)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado"));
        
        // Convertir String a MedicalCenterE
        com.example.MiHistoriaClinica.util.constant.MedicalCenterE medicalCenterEnum = 
            com.example.MiHistoriaClinica.util.constant.MedicalCenterE.getEnumFromName(medicalCenter);
        
        customRepositoryAccess.createTurno(patient, medic, request, medicalCenterEnum);
    }

    @Override
    public void reserveTurno(Long patientId, Long turnoId) {
        Turnos turno = turnosRepository.findById(turnoId)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));

        // Validar que el turno esté disponible
        if (!turno.isAvailable()) {
            if (turno.getPatient() != null) {
                throw new RuntimeException("El turno ya está reservado por otro paciente");
            } else {
                throw new RuntimeException("El turno está bloqueado y no puede ser reservado");
            }
        }

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        turno.setPatient(patient);
        turno.setAvailable(false);
        
        // Copiar información del paciente a campos denormalizados
        turno.setPatientName(patient.getName());
        turno.setPatientLastname(patient.getLastname());
        turno.setPatientDni(patient.getDni());
        turno.setPatientEmail(patient.getEmail());

        turnosRepository.save(turno);
        
        // Enviar email de confirmación de turno
        try {
            emailService.sendTurnoConfirmationEmail(patient, turno);
        } catch (Exception e) {
            System.err.println("Error enviando email de confirmación de turno: " + e.getMessage());
        }
    }

    @Override
    public void reserveTurnoByDni(Long patientDni, Long turnoId) {
        Turnos turno = turnosRepository.findById(turnoId)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));

        // Validar que el turno esté disponible
        if (!turno.isAvailable()) {
            if (turno.getPatient() != null) {
                throw new RuntimeException("El turno ya está reservado por otro paciente");
            } else {
                throw new RuntimeException("El turno está bloqueado y no puede ser reservado");
            }
        }

        // Buscar paciente por DNI en lugar de por ID
        Patient patient = patientRepository.findByDni(patientDni);
        if (patient == null) {
            throw new ResourceNotFoundException("Paciente no encontrado con DNI: " + patientDni);
        }

        turno.setPatient(patient);
        turno.setAvailable(false);
        
        // Copiar información del paciente a campos denormalizados
        turno.setPatientName(patient.getName());
        turno.setPatientLastname(patient.getLastname());
        turno.setPatientDni(patient.getDni());
        turno.setPatientEmail(patient.getEmail());

        turnosRepository.save(turno);
        
        // Enviar email de confirmación de turno
        try {
            emailService.sendTurnoConfirmationEmail(patient, turno);
        } catch (Exception e) {
            System.err.println("Error enviando email de confirmación de turno: " + e.getMessage());
        }
    }

    @Override
    public void liberarTurno(Long turnoId) {
        Turnos turno = turnosRepository.findById(turnoId)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
        
        // Validar que el turno no esté ya disponible
        if (turno.isAvailable()) {
            throw new RuntimeException("El turno ya está disponible");
        }
        
        // Guardar referencia al paciente antes de liberar (para enviar email)
        Patient patient = turno.getPatient();
        
        // Crear copia de los datos del turno para el email (antes de limpiarlos)
        Turnos turnoParaEmail = new Turnos();
        turnoParaEmail.setFechaTurno(turno.getFechaTurno());
        turnoParaEmail.setHoraTurno(turno.getHoraTurno());
        turnoParaEmail.setMedicFullName(turno.getMedicFullName());
        turnoParaEmail.setMedicSpecialty(turno.getMedicSpecialty());
        turnoParaEmail.setMedicalCenter(turno.getMedicalCenter());
        
        // Liberar el turno: quitar paciente y marcar como disponible
        // Funciona tanto para turnos reservados como bloqueados
        turno.setPatient(null);
        turno.setAvailable(true);
        
        // Limpiar campos denormalizados del paciente
        turno.setPatientName(null);
        turno.setPatientLastname(null);
        turno.setPatientDni(null);
        turno.setPatientEmail(null);
        
        turnosRepository.save(turno);
        
        // Enviar email de cancelación solo si había un paciente asignado
        if (patient != null && patient.getEmail() != null) {
            try {
                emailService.sendTurnoCancellationEmail(patient, turnoParaEmail);
            } catch (Exception e) {
                System.err.println("Error enviando email de cancelación de turno: " + e.getMessage());
            }
        }
    }

    @Override
    public void bloquearTurno(Long turnoId) {
        Turnos turno = turnosRepository.findById(turnoId)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
        
        // Validar que el turno no esté ya bloqueado
        if (!turno.isAvailable() && turno.getPatient() == null) {
            throw new RuntimeException("El turno ya está bloqueado");
        }
        
        // Validar que el turno no esté reservado
        if (!turno.isAvailable() && turno.getPatient() != null) {
            throw new RuntimeException("No se puede bloquear un turno que está reservado. Primero cancele la reserva.");
        }
        
        // Bloquear el turno: marcar como no disponible sin asignar paciente
        turno.setPatient(null);
        turno.setAvailable(false);
        
        turnosRepository.save(turno);
    }

    @Override
    public List<Turnos> getMisTurnos(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
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
            dto.getAvailableTurnos().add(new TurnoDisponibleDTO(
                t.getTurnoId(),
                t.getFechaTurno().toString(),
                t.getHoraTurno().toString()
            ));
        }

        return new java.util.ArrayList<>(map.values());
    }

    public List<MedicTurnosDTO> searchAvailableTurnosBySpecialtyAndDateRange(String specialty, String startDateIso) {
        java.time.LocalDate startDate = java.time.LocalDate.parse(startDateIso);
        java.time.LocalDate endDate = startDate.plusDays(29); // 30 días incluyendo el de inicio
        com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE specEnum = com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE.getEnumFromName(specialty);

        List<Turnos> turnos = turnosRepository.findByMedicSpecialtyAndFechaTurnoBetweenAndAvailableTrue(specEnum, startDate, endDate);

        java.util.Map<Long, MedicTurnosDTO> map = new java.util.HashMap<>();

        for (Turnos t : turnos) {
            MedicTurnosDTO dto = map.computeIfAbsent(
                    t.getMedic().getMedicId(),
                    id -> new MedicTurnosDTO(id, t.getMedicFullName(), t.getMedicSpecialty(), t.getMedicalCenter().getName(), new java.util.ArrayList<>())
            );
            dto.getAvailableTurnos().add(new TurnoDisponibleDTO(
                t.getTurnoId(),
                t.getFechaTurno().toString(),
                t.getHoraTurno().toString()
            ));
        }

        return new java.util.ArrayList<>(map.values());
    }

    public List<String> getMedicsWithAvailableTurnosBySpecialty(String specialty, String startDateIso) {
        java.time.LocalDate startDate = java.time.LocalDate.parse(startDateIso);
        java.time.LocalDate endDate = startDate.plusDays(29);
        com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE specEnum = com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE.getEnumFromName(specialty);
        List<Turnos> turnos = turnosRepository.findByMedicSpecialtyAndFechaTurnoBetweenAndAvailableTrue(specEnum, startDate, endDate);
        java.util.Set<String> medics = new java.util.HashSet<>();
        for (Turnos t : turnos) {
            medics.add(t.getMedic().getName() + " " + t.getMedic().getLastname());
        }
        return new java.util.ArrayList<>(medics);
    }


}


