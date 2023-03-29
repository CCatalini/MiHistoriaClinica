package com.example.MiHistoriaClinica.repository;

import com.example.MiHistoriaClinica.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {

    /*
     * CrudRepository implementa la mayoría de los métodos de acceso a la base que vamos a usar
     * se le pasa la entidad que vamos a usar y el tipo de dato del id
     */
}
