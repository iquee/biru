package com.luiztaira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Class to run the Spring Boot application with embedded services
 *
 * @author taira
 *
 */
@SpringBootApplication
@EnableMongoRepositories
public class BiruApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiruApplication.class, args);
	}
}
