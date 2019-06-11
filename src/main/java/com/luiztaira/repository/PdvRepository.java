package com.luiztaira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
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
	
    @Query("{coverageArea: {$geoIntersects: {$geometry: {type: 'Point' ,coordinates: [?0, ?1]}}}})")
	List<Pdv> searchIfInsideCoverageArea(Double lng, Double lat);
    
    @Query("{address: {$nearSphere: {$geometry: {type: 'Point' ,coordinates: [?0, ?1]}, $minDistance: 1, $maxDistance: 50000}}})")
	List<Pdv> searchNearestPdv(Double lng, Double lat);
}