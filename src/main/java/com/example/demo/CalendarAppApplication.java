package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages={ "com.wavelabs.resources", "com.wavelabs.service",
		"com.wavelabs.dao" })
@SpringBootApplication
public class CalendarAppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CalendarAppApplication.class, args);
	}
}
