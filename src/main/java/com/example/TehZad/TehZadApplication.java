package com.example.TehZad;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class TehZadApplication {

	public static void main(String[] args) {
		SpringApplication.run(TehZadApplication.class, args);
	}



}
