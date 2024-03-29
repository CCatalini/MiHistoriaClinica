package com.example.MiHistoriaClinica.persistence.repository;


import com.example.MiHistoriaClinica.persistence.model.*;
import com.example.MiHistoriaClinica.presentation.dto.*;
import com.example.MiHistoriaClinica.service.implementation.EmailService;
import com.example.MiHistoriaClinica.util.constant.AnalysisE;
import com.example.MiHistoriaClinica.util.constant.BloodTypeE;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Clase para realizar métodos personalizados
 * no reemplaza a PatientRepository
 */
@Repository
public class CustomRepositoryAccess {

    /** Utiliza una instancia de PatientRepository para realizar la operación de guardado de un DTO en la tabla PatientModel con el método saveDTO.*/
    private final PatientRepository patientRepository;
    private final MedicRepository medicRepository;
    private final MedicalFileRepository medicalFileRepository;
    private final MedicineRepository medicineRepository;
    private final AnalysisRepository analysisRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final TurnosRepository turnosRepository;

    @Autowired
    public CustomRepositoryAccess(PatientRepository repository, MedicRepository medicRepository, MedicalFileRepository medicalFileRepository, MedicineRepository medicineRepository, AnalysisRepository analysisRepository, MedicalAppointmentRepository medicalAppointmentRepository, TurnosRepository turnosRepository) {
        this.patientRepository = repository;

        this.medicRepository = medicRepository;
        this.medicalFileRepository = medicalFileRepository;
        this.medicineRepository = medicineRepository;
        this.analysisRepository = analysisRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;

        this.turnosRepository = turnosRepository;
    }



    public Patient saveDTO(PatientDTO patientDTO) {
        Patient patientSaved = new Patient();

        patientSaved.setName(patientDTO.getName());
        patientSaved.setLastname(patientDTO.getLastname());
        patientSaved.setPassword(patientDTO.getPassword());
        patientSaved.setDni(patientDTO.getDni());
        patientSaved.setEmail(patientDTO.getEmail());
        patientSaved.setEmailConfirmed(false);
        patientRepository.save(patientSaved);

        return patientSaved;
    }

    private String generateConfirmationLink(String email) {
        // Puedes utilizar alguna lógica para generar un token único
        // En este ejemplo, simplemente concatenamos el email y un timestamp
        long timestamp = System.currentTimeMillis();
        return "http://tu-aplicacion.com/confirmar-correo?token=" + email + "_" + timestamp;
    }

    public Medic saveMedicDto(MedicDTO medicDTO) {
        Medic medicSaved = new Medic();

        MedicalSpecialtyE specialty = MedicalSpecialtyE.getEnumFromName(medicDTO.getSpecialty());

        medicSaved.setName(medicDTO.getName());
        medicSaved.setLastname(medicDTO.getLastname());
        medicSaved.setDni(medicDTO.getDni());
        medicSaved.setEmail(medicDTO.getEmail());
        medicSaved.setPassword(medicDTO.getPassword());
        medicSaved.setMatricula(medicDTO.getMatricula());
        medicSaved.setSpecialty(specialty);

        return medicRepository.save(medicSaved);
    }

    public MedicalFile auxMedicalHistory(Optional<Patient> patient, MedicalFileDTO update){
        MedicalFile medicalFile;

        if(patient.isPresent() && patient.get().getMedicalFile()!= null )    medicalFile = patient.get().getMedicalFile();
        else                                                                    medicalFile = new MedicalFile();

        return setDatos(medicalFile, update ,patient.get());
    }

    private MedicalFile setDatos(MedicalFile medicalFile, MedicalFileDTO update, Patient patient) {
        BloodTypeE bloodType = BloodTypeE.getEnumFromName(update.getBloodType());

        medicalFile.setWeight(update.getWeight());
        medicalFile.setHeight(update.getHeight());
        medicalFile.setAllergy(update.getAllergy());
        medicalFile.setBloodType(bloodType);
        medicalFile.setActualMedicine(update.getActualMedicine());
        medicalFile.setChronicDisease(update.getChronicDisease());
        medicalFile.setFamilyMedicalHistory(update.getFamilyMedicalHistory());
        medicalFile.setPatient(patient);

        return medicalFileRepository.save(medicalFile);
    }

    public Medicine createPatientMedicine(MedicineDTO medicine, Optional<Patient> patient) {

        Medicine medicineSaved = new Medicine();
        Patient aux = patient.get();

        medicineSaved.setName(medicine.getName());
        medicineSaved.setDescription(medicine.getDescription());
        medicineSaved.setComments(medicine.getComments());
        medicineSaved.setStatus("Pendiente");
        medicineSaved.setPrescriptionDay(medicine.getPrescriptionDay());

        medicineSaved.addPatient(aux);
        aux.getMedicines().add(medicineSaved);

               patientRepository.save(aux);
        return medicineRepository.save(medicineSaved);
    }

    public Analysis createPatientAnalysis(AnalysisDTO analysisDTO, Optional<Patient> patient) {
        Analysis analysisSaved = new Analysis();
        Patient aux = patient.get();
        AnalysisE name = AnalysisE.getEnumFromName(analysisDTO.getName());
        MedicalCenterE medicalCenter = MedicalCenterE.getEnumFromName(analysisDTO.getMedicalCenterE());

        analysisSaved.setName(name);
        assert name != null;
        analysisSaved.setDescription(name.getDescription());
        analysisSaved.setStatus("Pendiente");
        analysisSaved.setMedicalCenterE(medicalCenter);

        aux.getAnalysis().add(analysisSaved);

               patientRepository.save(aux);
        return analysisRepository.save(analysisSaved);
    }

    public MedicalAppointment createMedicalAppointment(MedicalAppointmentDTO appointmentDTO, Optional<Patient> patient, Optional<Medic> medic) {
        MedicalAppointment medicalAppointmentSaved = new MedicalAppointment();

        medicalAppointmentSaved.setAppointmentReason(appointmentDTO.getAppointmentReason());
        medicalAppointmentSaved.setObservations(appointmentDTO.getObservations());
        medicalAppointmentSaved.setCurrentIllness(appointmentDTO.getCurrentIllness());
        medicalAppointmentSaved.setPhysicalExam(appointmentDTO.getPhysicalExam());

        medicalAppointmentSaved.setPatient(patient.get());

        medicalAppointmentSaved.setMedicFullName(medic.get().getName() + " " + medic.get().getLastname());
        medicalAppointmentSaved.setMatricula(medic.get().getMatricula());
        medicalAppointmentSaved.setSpecialty(medic.get().getSpecialty());

        return  medicalAppointmentRepository.save(medicalAppointmentSaved);

    }

    public Turnos createTurno(Patient patient, Medic medic, TurnoDTO request, String medicalCenter) {
        Turnos turnoSaved = new Turnos();

        turnoSaved.setFechaTurno(request.getFechaTurno());
        turnoSaved.setHoraTurno(request.getHoraTurno());

        turnoSaved.setMedicFullName(medic.getName() + " " + medic.getLastname());
        turnoSaved.setMedicSpecialty(medic.getSpecialty());

        turnoSaved.setMedicalCenter(medicalCenter);

        turnoSaved.setPatient(patient);

        return turnosRepository.save(turnoSaved);

    }

}
