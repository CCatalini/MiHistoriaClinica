package com.example.MiHistoriaClinica.persistence.repository;


import com.example.MiHistoriaClinica.persistence.model.*;
import com.example.MiHistoriaClinica.presentation.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Clase para realizar métodos personalizados
 * no reemplaza a PatientRepository
 */
@Repository
public class CustomRepositoryAccess {

    /** utiliza una instancia de PatientRepository para realizar la operación de guardado de un DTO en la tabla PatientModel con el método saveDTO.*/
    private final PatientRepository patientRepository;
    private final MedicRepository medicRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MedicineRepository medicineRepository;
    private final AnalysisRepository analysisRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final TurnosRepository turnosRepository;

    @Autowired
    public CustomRepositoryAccess(PatientRepository repository, MedicRepository medicRepository, MedicalHistoryRepository medicalHistoryRepository, MedicineRepository medicineRepository, AnalysisRepository analysisRepository, MedicalAppointmentRepository medicalAppointmentRepository, TurnosRepository turnosRepository) {
        this.patientRepository = repository;

        this.medicRepository = medicRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.medicineRepository = medicineRepository;
        this.analysisRepository = analysisRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;

        this.turnosRepository = turnosRepository;
    }


    public Patient saveDTO(PatientDTO patientDTO) {
        Patient patientSaved = new Patient();
        // Establecer los valores del DTO en la entidad a guardar
        patientSaved.setName(patientDTO.getName());
        patientSaved.setLastname(patientDTO.getLastname());
        patientSaved.setPassword(patientDTO.getPassword());
        patientSaved.setDni(patientDTO.getDni());
        patientSaved.setEmail(patientDTO.getEmail());

        return patientRepository.save(patientSaved);
    }


    public Medic saveMedicDto(MedicDTO medicDTO) {
        Medic medicSaved = new Medic();

        medicSaved.setName(medicDTO.getName());
        medicSaved.setLastname(medicDTO.getLastname());
        medicSaved.setDni(medicDTO.getDni());
        medicSaved.setEmail(medicDTO.getEmail());
        medicSaved.setPassword(medicDTO.getPassword());
        medicSaved.setMatricula(medicDTO.getMatricula());
        medicSaved.setSpecialty(medicDTO.getSpecialty());

        return medicRepository.save(medicSaved);

    }



    public MedicalHistory auxMedicalHistory(Optional<Patient> patient, MedicalHistoryDTO update){
        MedicalHistory medicalHistory ;

        if(patient.isPresent() && patient.get().getMedicalHistory()!= null )    medicalHistory = patient.get().getMedicalHistory();
        else                                                                    medicalHistory = new MedicalHistory();

        return setDatos(medicalHistory, update ,patient.get());
    }

    private MedicalHistory setDatos(MedicalHistory medicalHistory, MedicalHistoryDTO update, Patient patient) {
        medicalHistory.setWeight(update.getWeight());
        medicalHistory.setHeight(update.getHeight());
        medicalHistory.setAllergy(update.getAllergy());
        medicalHistory.setBloodType(update.getBloodType());
        medicalHistory.setActualMedicine(update.getActualMedicine());
        medicalHistory.setChronicDisease(update.getChronicDisease());
        medicalHistory.setFamilyMedicalHistory(update.getFamilyMedicalHistory());
        medicalHistory.setPatient(patient);

        return medicalHistoryRepository.save(medicalHistory);
    }

    public Medicine createPatientMedicine(MedicineDTO medicine, Optional<Patient> patient) {

        Medicine medicineSaved = new Medicine();
        Patient aux = patient.get();

        medicineSaved.setMedicineName(medicine.getMedicineName());
        medicineSaved.setLab(medicine.getLab());
        medicineSaved.setStatus(medicine.getStatus());
        medicineSaved.setDescription(medicine.getDescription());
        medicineSaved.setActiveIngredient(medicine.getActiveIngredient());

        medicineSaved.addPatient(aux);
        aux.getMedicines().add(medicineSaved);

                patientRepository.save(aux);
        return medicineRepository.save(medicineSaved);
    }

    public Analysis createPatientAnalysis(AnalysisDTO analysisDTO, Optional<Patient> patient) {
        Analysis analysisSaved = new Analysis();

        analysisSaved.setMedicalCenter(analysisDTO.getMedicalCenter());
        analysisSaved.setDescription(analysisDTO.getDescription());
        analysisSaved.setName(analysisDTO.getName());

        analysisSaved.addPatient(patient.get());
        patient.get().getAnalysis().add(analysisSaved);

                patientRepository.save(patient.get());
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
