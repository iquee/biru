package com.luiztaira;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.github.mongobee.Mongobee;

/**
 * Class to run the Spring Boot application with embedded services
 *
 * @author taira
 *
 */
@SpringBootApplication
@EnableMongoRepositories
public class SpringGeoMongo {
	
	@Value("${spring.data.mongodb.host}")
	private String mongoHost;
	@Value("${spring.data.mongodb.port}")
	private String mongoPort;
	@Value("${spring.data.mongodb.database}")
	private String mongoSchema;

	public static void main(String[] args) {
		SpringApplication.run(SpringGeoMongo.class, args);
	}

	@Bean
	public Mongobee mongobee() {
		String uri = "mongodb://" + mongoHost + ":" + mongoPort + "/" + mongoSchema;		
		Mongobee runner = new Mongobee(uri);
		runner.setDbName(mongoSchema);
		runner.setChangeLogsScanPackage("com.luiztaira.changelog");

		return runner;
	}
}
