package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.model.UserModel;
import com.example.MiHistoriaClinica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    public ArrayList<UserModel> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/postUser")
    public UserModel saveUser(@RequestBody UserModel user){
        return this.userService.saveUser(user);
    }


}
