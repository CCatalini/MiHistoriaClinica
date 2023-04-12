package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.paraDiseñoRoles.RoleModel;
import com.example.MiHistoriaClinica.model.PacientModel;
import com.example.MiHistoriaClinica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
//Los servicios usan al repositorio para ejecutar la lógica de la app
public class UserService {

    @Autowired //esto hace que no haya que instanciar a la variable, Spring sabe que existe una instancia y cual tiene que utilizar

    UserRepository userRepository;

    /** Para registrar un usuario en la BD
     * @param user que quiero guardar (sin id)
     * @return user guardado con el id que se autogeneró
     */
    public PacientModel saveUser(PacientModel user){
        /*
        si en postman NO envío el usuario se crea uno nuevo
        si en postman envío el id se actualiza el usuario ya existente
         */
        return userRepository.save(user);
    }

    public Iterable<PacientModel> saveAllUsers(Iterable<PacientModel> users) {
        return userRepository.saveAll(users);
    }

    public Optional<PacientModel> findById(Long id){
        return userRepository.findById(id);
    }

    public boolean existsUserById(Long id){
        return userRepository.existsById(id);
    }

    /** método para que el servicio use al repositorio
     * @return todos los usuarios registrados de la BD
     */
    public ArrayList<PacientModel> findAllUsers(){
        // se castea para poder meterlo en un JSON
        return (ArrayList<PacientModel>) userRepository.findAll();
    }

    public void /*ArrayList<UserModel>*/ findUserByRole (RoleModel role){
        /**
         *
         */
    }

    public Iterable<PacientModel> findAllUsersById(Iterable<Long> id){
        return userRepository.findAllById(id);
    }

    public long countUsers (){
        return userRepository.count();
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public void deleteUser(PacientModel user){
        userRepository.delete(user);
    }

    public void deleteAllById(Iterable<Long> ids){
        userRepository.deleteAllById(ids);
    }

    public void deleteAllUsers(Iterable<PacientModel> users){
        userRepository.deleteAll(users);
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }





    private void /*Iterable<Long>*/ encontrarIdsPorRol(){
        /**
         * ¿armar un iterador con los id de los usuarios con un solo rol para obtenerlo stodos con un finAll (pasando este iterados???????????
         */
    }
}
