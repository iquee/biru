package com.luiztaira.web.rest;

import static com.luiztaira.web.rest.BaseController.API;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.luiztaira.exception.ExceptionResponse;
import com.luiztaira.exception.PdvException;
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
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully pdv created"),
			@ApiResponse(code = 401, message = "You are not authorized to create a pdv"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Long create(@Valid @RequestBody PdvRequestDTO requestDto) throws PdvException {
		return pdvService.createOrUpdate(requestDto);
	}

	/**
	 * @param Code
	 * @return {@link PdvResponseDTO}
	 */
	@ApiOperation(value = "Get pdv by id", response = PdvResponseDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "PdvResponseDTO object"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PdvResponseDTO getById(@PathVariable("id") Long id) {
		return pdvService.getById(id);
	}

	/**
	 * @param name
	 * @return {@link PdvResponseDTO}
	 */
	@ApiOperation(value = "Find nearest pdv from specific location", response = PdvResponseDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Given a specific lng & lat, return nearest pdv. Ex.: -49.379279, -20.816612") })
	@RequestMapping(value = "/findNearestPdv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PdvResponseDTO search(@RequestParam List<Double> coordinates) {
		PdvResponseDTO pdv = pdvService.search(coordinates);
		if(pdv == null)
			new PdvResponseDTO();
		return pdv;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<ExceptionResponse> handleConflictException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Document already registred",
				HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getDescription(false).replaceAll("uri=", ""),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}