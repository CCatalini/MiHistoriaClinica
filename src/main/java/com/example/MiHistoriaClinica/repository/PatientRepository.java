package com.example.MiHistoriaClinica.repository;


import com.example.MiHistoriaClinica.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepository extends JpaRepository<PatientModel, Long> {

    PatientModel findByDniAndPassword(Long dni, String password);

    PatientModel findByDni(Long dni);

    void deleteByDni(Long dni);

    PatientModel findByLinkCode(String linkCode);

    PatientModel findByLinkCodeAndDni(String linkCode, String dni);

    // void assignRole(Long patient_id, Long role_id);
}
