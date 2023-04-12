package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.RoleModel;
import com.example.MiHistoriaClinica.model.UserModel;
import com.example.MiHistoriaClinica.repository.RoleRepository;
import com.example.MiHistoriaClinica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    @PostMapping
    public UserModel createUser(@RequestBody UserModel user) {
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public UserModel getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/getAllUsers")
    public ArrayList<UserModel> getAllUsers(){
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    @PutMapping("/{id}")
    public UserModel updateUser(@PathVariable Long id, @RequestBody UserModel newUser) {
        UserModel userModel = userRepository.findById(id).orElse(null);
        if (userModel != null) {
            userModel.setName(newUser.getName());
            userModel.setLastname(newUser.getLastname());
            userModel.setEmail(newUser.getEmail());
            userModel.setPassword(newUser.getPassword());
            userModel.setDni(newUser.getDni());
            userModel.setBirthdate(newUser.getBirthdate());

            userModel.setUserRoles(newUser.getUserRoles());

                return userRepository.save(userModel);
        } else  return null;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        UserModel existingUser = userRepository.findById(id).orElseThrow(()
                                -> new ResourceNotFoundException("User not found"));
        userRepository.delete(existingUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public void deleteAllUser(){
        userRepository.deleteAll();
    }

    @PostMapping("/{usuarioId}/roles/{rolId}")
    public UserModel asignarRol(@PathVariable Long usuarioId, @PathVariable Long rolId) {
        UserModel usuario = userRepository.findById(usuarioId).orElse(null);
        RoleModel rol = roleRepository.findById(rolId).orElse(null);
        if (usuario != null && rol != null) {
            usuario.getUserRoles().add(rol);
            return userRepository.save(usuario);
        } else {
            return null;
        }
    }


}





/*
  @PostMapping("/postUser")
    public UserModel postUser(@RequestBody UserModel user){
        return this.userService.saveUser(user);
    }

    @PostMapping("/postAllUsers")
    public Iterable<UserModel> postAllUsers(Iterable<UserModel> users){
        return userService.saveAllUsers(users);
    }

    @GetMapping()
    public ArrayList<UserModel> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping()
    public Optional<UserModel> getById(Long id){
        return userService.findById(id);
    }

 */
