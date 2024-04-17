package com.typology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
//@ComponentScan(basePackages = {"com.typology"})
//@EnableWebSecurity(debug = true)
public class TypologyApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TypologyApiApplication.class, args);
	}
}
