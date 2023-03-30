package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.model.UserModel;
import com.example.MiHistoriaClinica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/postUser")
    public UserModel saveUser(@RequestBody UserModel user){
        return this.userService.saveUser(user);
    }

    @PostMapping("/postAllUsers")
    public Iterable<UserModel> saveAllUsers(Iterable<UserModel> users){
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




}
