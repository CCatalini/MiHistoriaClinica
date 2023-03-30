package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.model.RoleModel;
import com.example.MiHistoriaClinica.model.UserModel;
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
    public UserModel saveUser(UserModel user){
        /*
        si en postman NO envío el usuario se crea uno nuevo
        si en postman envío el id se actualiza el usuario ya existente
         */
        return userRepository.save(user);
    }

    public Iterable<UserModel> saveAllUsers(Iterable<UserModel> users) {
        return userRepository.saveAll(users);
    }

    public Optional<UserModel> findById(Long id){
        return userRepository.findById(id);
    }

    public boolean existUserById(Long id){
        return userRepository.existsById(id);
    }

    /** método para que el servicio use al repositorio
     * @return todos los usuarios registrados de la BD
     */
    public ArrayList<UserModel> findAllUsers(){
        // se castea para poder meterlo en un JSON
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public ArrayList<UserModel> findUserByRole (RoleModel role){

    }

    public Iterable<UserModel> findAllUsersById(Iterable<Long> id){
        return userRepository.findAllById(id);
    }

    public long countUsers (){
        return userRepository.count();
    }




    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public void delete(UserModel model){
        userRepository.delete(model);
    }





    private Iterable<Long> encontrarIdsPorRol(){
        /**
         * armar un iterador con los id de los usuarios con un solo rol para obtenerlo stodos con un finAll (pasando este iterados???????????
         */
    }
}
