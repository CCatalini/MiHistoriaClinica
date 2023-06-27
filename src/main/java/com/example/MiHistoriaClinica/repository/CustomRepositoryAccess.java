package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.dto.*;
import com.example.MiHistoriaClinica.model.*;
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

    @Autowired
    public CustomRepositoryAccess(PatientRepository repository, MedicRepository medicRepository, MedicalHistoryRepository medicalHistoryRepository, MedicineRepository medicineRepository, AnalysisRepository analysisRepository) {
        this.patientRepository = repository;

        this.medicRepository = medicRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.medicineRepository = medicineRepository;
        this.analysisRepository = analysisRepository;
    }


    public PatientModel saveDTO(PatientDTO patientDTO) {
        PatientModel patientSaved = new PatientModel();
        // Establecer los valores del DTO en la entidad a guardar
        patientSaved.setName(patientDTO.getName());
        patientSaved.setLastname(patientDTO.getLastname());
        patientSaved.setPassword(patientDTO.getPassword());
        patientSaved.setDni(patientDTO.getDni());
        patientSaved.setEmail(patientDTO.getEmail());

        return patientRepository.save(patientSaved);
    }


    public MedicModel saveMedicDto(MedicDTO medicDTO) {
        MedicModel medicSaved = new MedicModel();

        medicSaved.setName(medicDTO.getName());
        medicSaved.setLastname(medicDTO.getLastname());
        medicSaved.setDni(medicDTO.getDni());
        medicSaved.setEmail(medicDTO.getEmail());
        medicSaved.setPassword(medicDTO.getPassword());
        medicSaved.setMatricula(medicDTO.getMatricula());
        medicSaved.setSpecialty(medicDTO.getSpecialty());

        return medicRepository.save(medicSaved);

    }



    public MedicalHistoryModel auxMedicalHistory(Optional<PatientModel> patient, MedicalHistoryDTO update){
        MedicalHistoryModel medicalHistory ;

        if(patient.isPresent() && patient.get().getMedicalHistory()!= null )    medicalHistory = patient.get().getMedicalHistory();
        else                                                                    medicalHistory = new MedicalHistoryModel();

        return setDatos(medicalHistory, update ,patient.get());
    }

    private MedicalHistoryModel setDatos(MedicalHistoryModel medicalHistory, MedicalHistoryDTO update, PatientModel patientModel) {
        medicalHistory.setWeight(update.getWeight());
        medicalHistory.setHeight(update.getHeight());
        medicalHistory.setAllergy(update.getAllergy());
        medicalHistory.setBloodType(update.getBloodType());
        medicalHistory.setActualMedicine(update.getActualMedicine());
        medicalHistory.setChronicDisease(update.getChronicDisease());
        medicalHistory.setFamilyMedicalHistory(update.getFamilyMedicalHistory());
        medicalHistory.setPatient(patientModel);

        return medicalHistoryRepository.save(medicalHistory);
    }

    public MedicineModel createPatientMedicine(MedicineDTO medicine, Optional<PatientModel> patient) {

        MedicineModel medicineSaved = new MedicineModel();
        PatientModel aux = patient.get();

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

    public AnalysisModel createPatientAnalysis(AnalysisDTO analysisDTO, Optional<PatientModel> patient) {
        AnalysisModel analysisSaved = new AnalysisModel();

        analysisSaved.setMedicalCenter(analysisDTO.getMedicalCenter());
        analysisSaved.setDescription(analysisDTO.getDescription());
        analysisSaved.setName(analysisDTO.getName());

        analysisSaved.addPatient(patient.get());
        patient.get().getAnalysis().add(analysisSaved);

                patientRepository.save(patient.get());
        return analysisRepository.save(analysisSaved);
    }


}
