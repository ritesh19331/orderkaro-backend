package com.orderkaro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class OrderkaroApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderkaroApplication.class, args);
	}

}
