package com.poo.proyecto;

import com.poo.proyecto.util.PasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableJpaAuditing
public class ProyectoApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProyectoApplication.class, args);


	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder();
	}

}
