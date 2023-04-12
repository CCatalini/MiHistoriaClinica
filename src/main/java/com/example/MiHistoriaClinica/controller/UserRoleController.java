package com.example.MiHistoriaClinica.controller;

import com.example.MiHistoriaClinica.repository.UserRoleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    UserRoleRepository userRoleRepository;


}
