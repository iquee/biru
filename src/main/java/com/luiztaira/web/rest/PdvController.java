package com.luiztaira.web.rest;

import static com.luiztaira.web.rest.BaseController.API;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RestController;

import com.luiztaira.exception.PdvServerException;
import com.luiztaira.service.PdvService;
import com.luiztaira.web.rest.dto.PdvRequestDTO;
import com.luiztaira.web.rest.dto.PdvResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(API + "/pdv")
@Api(value = "PDV controller")
public class PdvController extends BaseController {

	Logger logger = LoggerFactory.getLogger(PdvController.class);

	@Autowired
	PdvService pdvService;

	/**
	 * @param PdvRequestDTO
	 * @return Long
	 * @throws Exception
	 */
	@ApiOperation(value = "Create a single pdv", response = Long.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Successfully pdv created"),
			@ApiResponse(code = 401, message = "You are not authorized to create a pdv"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 409, message = "Conflict") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Long> create(@Valid @RequestBody PdvRequestDTO requestDto) throws PdvServerException {
		return new ResponseEntity<Long>(pdvService.createOrUpdate(requestDto), HttpStatus.CREATED);
	}

	/**
	 * @param Code
	 * @return {@link PdvResponseDTO}
	 */
	@ApiOperation(value = "Get pdv by id", response = PdvResponseDTO.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "PdvResponseDTO object"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<PdvResponseDTO> getById(@PathVariable("id") Long id) {		
		return new ResponseEntity<PdvResponseDTO>(pdvService.getById(id), HttpStatus.OK);
	}

	/**
	 * @param name
	 * @return {@link PdvResponseDTO}
	 */
	@ApiOperation(value = "Find nearest pdv from specific location", response = PdvResponseDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Given a specific lng & lat, return nearest pdv. Ex.: -49.379279, -20.816612"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/findNearestPdv", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<PdvResponseDTO> search(@RequestParam List<Double> coordinates) {		
		return new ResponseEntity<PdvResponseDTO>(pdvService.search(coordinates), HttpStatus.OK);
	}
}