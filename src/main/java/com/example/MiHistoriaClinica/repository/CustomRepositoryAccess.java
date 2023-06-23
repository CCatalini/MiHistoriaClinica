package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.dto.MedicSignupDTO;
import com.example.MiHistoriaClinica.dto.MedicalHistoryDTO;
import com.example.MiHistoriaClinica.dto.MedicineDTO;
import com.example.MiHistoriaClinica.dto.PatientDTO;
import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.MedicalHistoryModel;
import com.example.MiHistoriaClinica.model.MedicineModel;
import com.example.MiHistoriaClinica.model.PatientModel;
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

    @Autowired
    public CustomRepositoryAccess(PatientRepository repository, MedicRepository medicRepository, MedicalHistoryRepository medicalHistoryRepository, MedicineRepository medicineRepository) {
        this.patientRepository = repository;

        this.medicRepository = medicRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.medicineRepository = medicineRepository;
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



    public MedicModel saveMedicDto(MedicSignupDTO medicDTO) {
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



    public MedicalHistoryModel createPatientMedicalHistory(MedicalHistoryDTO medicalHistory, Optional<PatientModel> patient) {

        MedicalHistoryModel historySaved = new MedicalHistoryModel();

        historySaved.setWeight(medicalHistory.getWeight());
        historySaved.setHeight(medicalHistory.getHeight());
        historySaved.setAllergy(medicalHistory.getAllergy());
        historySaved.setBloodType(medicalHistory.getBloodType());
        historySaved.setActualMedicine(medicalHistory.getActualMedicine());
        historySaved.setChronicDisease(medicalHistory.getChronicDisease());
        historySaved.setFamilyMedicalHistory(medicalHistory.getFamilyMedicalHistory());

        historySaved.setPatient(patient.get());

        return medicalHistoryRepository.save(historySaved);

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
}
