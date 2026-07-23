package com.d3tec.template.d3tec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class D3tecApplication {

	public static void main(String[] args) {
		SpringApplication.run(D3tecApplication.class, args);
	}

}
