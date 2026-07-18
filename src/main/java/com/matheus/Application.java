package com.matheus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class Application {

	public static void main(String[] args) {
		System.out.println("JWT_SECRET usado: " + System.getenv("JWT_SECRET"));
		SpringApplication.run(Application.class, args);
	}

}

