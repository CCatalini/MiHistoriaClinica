package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.User_Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository <User_Role, Long> {
}
