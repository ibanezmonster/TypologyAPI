package com.typology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.typology"})
public class TypologyApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TypologyApiApplication.class, args);
	}
}
