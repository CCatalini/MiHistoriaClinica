package com.example.MiHistoriaClinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MiHistoriaClinicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiHistoriaClinicaApplication.class, args);
	}

}
