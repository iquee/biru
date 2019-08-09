package com.luiztaira.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luiztaira.domain.Restaurant;
import com.luiztaira.dto.RestaurantRequestDTO;
import com.luiztaira.dto.RestaurantResponseDTO;
import com.luiztaira.exception.RestaurantNotFoundException;
import com.luiztaira.exception.RestaurantServerException;
import com.luiztaira.repository.RestaurantRepository;
import com.luiztaira.service.RestaurantService;

@Component
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	RestaurantRepository repository;

	@Override
	public String create(RestaurantRequestDTO dto) {
		return repository.save(convert(dto)).getId();
	}

	@Override
	public RestaurantResponseDTO getById(String id) {
		Optional<Restaurant> optional = repository.findById(id);
		if (optional.isPresent())
			return RestaurantResponseDTO.convert(optional.get());
		throw new RestaurantNotFoundException("No Restaurant found for id: " + id.toString());
	}

	@Override
	public RestaurantResponseDTO search(List<Double> coordinates) throws RestaurantServerException {
		// check if location is inside any coverage area
		List<Restaurant> rs = repository.searchIfInsideCoverageArea(coordinates.get(0), coordinates.get(1));
		if (!rs.isEmpty()) {
			// if inside, search the nearest restaurant
			List<Restaurant> nearest = repository.searchNearestRestaurant(coordinates.get(0), coordinates.get(1));
			return RestaurantResponseDTO.convert(nearest.get(0));
		} else {
			throw new RestaurantNotFoundException("No restaurant found in coverage area");
		}
	}

	@Override
	public Restaurant convert(RestaurantRequestDTO dto) {
		return Restaurant.convert(dto);
	}

}