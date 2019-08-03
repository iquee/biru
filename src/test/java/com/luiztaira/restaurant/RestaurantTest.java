package com.luiztaira.restaurant;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import com.luiztaira.dto.RestaurantRequestDTO;
import com.luiztaira.dto.RestaurantResponseDTO;
import com.luiztaira.exception.RestaurantNotFoundException;
import com.luiztaira.exception.RestaurantServerException;
import com.luiztaira.service.RestaurantService;

@SpringBootTest
public class RestaurantTest {

	private static RestaurantService service;

	@Autowired
	public RestaurantTest(RestaurantService service) {
		RestaurantTest.service = service;
	}
	
	@Test
	public void testCreateRestaurant() throws Exception {
		String id = service.create(buildRestaurant("Boteco Legal", "Zé Sorriso", "29.165.498/0001-24"));
		
		assertNotNull(id);
	}

	@Test
	public void testGetRestaurantById() throws Exception {
		String id = service.create(buildRestaurant("Restaurante Kiko", "João Risada", "20"));
		RestaurantResponseDTO Restaurant = service.getById(id);
		
		assertThat(Restaurant.getTradingName(), equalTo("Restaurante Kiko"));
		assertThat(Restaurant.getOwnerName(), equalTo("João Risada"));
	}

	@Test
	public void testSearchNearestRestaurant() throws Exception {
		List<Double> coordinates = new ArrayList<>();
		coordinates.add(-49.379279);
		coordinates.add(-20.816612);
		RestaurantResponseDTO Restaurant = service.search(coordinates);
		
		// based on restaurants.json file imported
		assertThat(Restaurant.getTradingName(), equalTo("Cantina Nem Tanto"));		
	}

	// fail tests
	@Test
	public void testSaveRestaurantExistedDocument() throws RestaurantServerException {
		DuplicateKeyException thrown = assertThrows(DuplicateKeyException.class,
				() -> service.create(buildRestaurant("Restaurante Ocoto", "João Felix", "20")),
				"Duplicate document");
		assertTrue(thrown.getMessage().contains("25221259"));
	}
	
	@Test
	public void testRestaurantNotFound() throws Exception {
		RestaurantNotFoundException thrown = assertThrows(RestaurantNotFoundException.class,
				() -> service.getById("x"), "No Restaurant found for id: x");
		assertTrue(thrown.getMessage().contains("No Restaurant found for id: x"));
	}

	private RestaurantRequestDTO buildRestaurant(String fantasyName, String ownerName, String document) {
		Map<String, Object> address = new HashMap<String, Object>();
		address.put("type", "Point");
		address.put("coordinates", Arrays.asList(-46.57421, -21.785741));

		List<List<Double>> pList1 = new ArrayList<List<Double>>();
		pList1.add(Arrays.asList(-43.521893, -23.00843));
		pList1.add(Arrays.asList(-43.52361, -23.029285));
		pList1.add(Arrays.asList(-43.43469, -23.051086));
		pList1.add(Arrays.asList(-43.367397, -23.019491));
		pList1.add(Arrays.asList(-43.366367, -22.973665));
		pList1.add(Arrays.asList(-43.388683, -22.973034));
		pList1.add(Arrays.asList(-43.393833, -22.949324));
		pList1.add(Arrays.asList(-43.508846, -22.961021));
		pList1.add(Arrays.asList(-43.521893, -23.00843));

		List<Object> multiPolygon = new ArrayList<Object>();
		multiPolygon.add(pList1);

		Map<String, Object> coverageArea = new HashMap<String, Object>();
		coverageArea.put("type", "MultiPolygon");
		coverageArea.put("coordinates", Arrays.asList(multiPolygon));
		
		RestaurantRequestDTO dto = new RestaurantRequestDTO();
		dto.setDocument(document);
		dto.setCoverageArea(coverageArea);
		dto.setAddress(address);
		dto.setFantasyName(fantasyName);
		dto.setOwnerName(ownerName);

		return dto;
	}

}
