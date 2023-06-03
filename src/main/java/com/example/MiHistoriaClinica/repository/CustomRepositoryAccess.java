package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.dto.MedicSignupDTO;
import com.example.MiHistoriaClinica.dto.PatientSignupDTO;
import com.example.MiHistoriaClinica.model.MedicModel;
import com.example.MiHistoriaClinica.model.PatientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Clase para realizar métodos personalizados
 * no reemplaza a PatientRepository
 */
@Repository
public class CustomRepositoryAccess {

    /** utiliza una instancia de PatientRepository para realizar la operación de guardado de un DTO en la tabla PatientModel con el método saveDTO.*/
    private final PatientRepository patientRepository;
    private final MedicRepository medicRepository;

    @Autowired
    public CustomRepositoryAccess(PatientRepository repository, MedicRepository medicRepository) {
        this.patientRepository = repository;

        this.medicRepository = medicRepository;
    }


    public PatientModel saveDTO(PatientSignupDTO patientDTO) {
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
}
