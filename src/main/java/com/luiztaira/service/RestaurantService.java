package com.luiztaira.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luiztaira.domain.Restaurant;
import com.luiztaira.dto.RestaurantRequestDTO;
import com.luiztaira.dto.RestaurantResponseDTO;
import com.luiztaira.exception.RestaurantServerException;
import com.luiztaira.utils.Converter;

/**
 * All services to manipulate entity {@link Restaurant}
 *
 * @author taira
 *
 */
@Service
public interface RestaurantService extends Converter<RestaurantRequestDTO, Restaurant> {

	/**
	 * Create or update a single restaurant
	 *
	 * @param dto
	 * @return restaurant id
	 *
	 * @throws RestaurantServerException
	 */
	String create(RestaurantRequestDTO dto);

	/**
	 * Find a restaurant by id
	 *
	 * @param id
	 * @return RestaurantResponseDTO
	 *
	 * @throws RestaurantServerException
	 */
	RestaurantResponseDTO getById(String id);

	/**
	 * @param coordinates
	 * @return RestaurantResponseDTO
	 *
	 * @throws RestaurantServerException
	 */
	RestaurantResponseDTO search(List<Double> coordinates);
}
