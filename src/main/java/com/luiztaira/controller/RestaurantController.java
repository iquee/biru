package com.luiztaira.controller;

import static com.luiztaira.controller.BaseController.API;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.luiztaira.dto.RestaurantRequestDTO;
import com.luiztaira.dto.RestaurantResponseDTO;
import com.luiztaira.exception.RestaurantServerException;
import com.luiztaira.service.RestaurantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(API + "/restaurant")
@Api(value = "Restaurant Controller")
public class RestaurantController extends BaseController {
	RestaurantService restService;	
	
	@Autowired
	public RestaurantController(RestaurantService restService){
		this.restService = restService;
	}
	
	@ApiOperation(value = "Create a single restaurant", response = String.class, code = 201)
	@ResponseStatus(value = HttpStatus.CREATED, code = HttpStatus.CREATED)	
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Successfully restaurant created"),
			@ApiResponse(code = 401, message = "You are not authorized to create a restaurant"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 409, message = "Conflict") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody ResponseEntity<String> create(@Valid @RequestBody RestaurantRequestDTO requestDto) throws RestaurantServerException {
		return new ResponseEntity<String>(restService.create(requestDto), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Get restaurant by id", response = RestaurantResponseDTO.class)
	@ResponseStatus(value = HttpStatus.OK, code = HttpStatus.OK)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "RestaurantResponseDTO object"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody ResponseEntity<RestaurantResponseDTO> getById(@PathVariable("id") String id) {		
		return new ResponseEntity<RestaurantResponseDTO>(restService.getById(id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Find nearest restaurant from specific location", response = RestaurantResponseDTO.class)
	@ResponseStatus(value = HttpStatus.OK, code = HttpStatus.OK)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Given a specific lng & lat, return nearest restaurant. Ex.: -49.379279, -20.816612"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/findNearestRestaurant", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody ResponseEntity<RestaurantResponseDTO> search(@RequestParam List<Double> coordinates) {		
		return new ResponseEntity<RestaurantResponseDTO>(restService.search(coordinates), HttpStatus.OK);
	}
}