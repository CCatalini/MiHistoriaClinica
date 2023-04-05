package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {


}
