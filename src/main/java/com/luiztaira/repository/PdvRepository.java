package com.luiztaira.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.luiztaira.domain.Pdv;

/**
 * Repository for PDV documents. Sample services are extended from {@link MongoRepository} 
 * Its not necessary to implement it. Spring Boot do it.
 * 
 * Custom query needed to be implemented
 *
 * @author taira
 *
 */
@Repository
public interface PdvRepository extends MongoRepository<Pdv, Long> {
	
	Optional<Pdv> findById(Long id);	
}