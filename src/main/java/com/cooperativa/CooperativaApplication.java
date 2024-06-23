package com.cooperativa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.cooperativa.repository")
public class CooperativaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CooperativaApplication.class, args);
	}

}
