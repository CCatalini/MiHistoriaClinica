package com.example.MiHistoriaClinica.repository;


import com.example.MiHistoriaClinica.dto.PatientSignupDTO;
import com.example.MiHistoriaClinica.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepository extends JpaRepository<PatientModel, Long> {

  //  PatientModel saveDTO(PatientSignupDTO  patient);

    PatientModel findByDniAndPassword(Long dni, String password);

    PatientModel findByDni(Long dni);

    void deleteByDni(Long dni);

    PatientModel findByLinkCode(String linkCode);


    // void assignRole(Long patient_id, Long role_id);
}
