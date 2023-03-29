package com.example.MiHistoriaClinica;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundo {

    @RequestMapping("/")
    public String holaMundo(){
        return "hola mundo";
    }
}
