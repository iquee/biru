package com.luiztaira.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.luiztaira.exception.RestaurantNotFoundException;
import com.luiztaira.exception.RestaurantServerException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {    
	
	@ExceptionHandler(RestaurantServerException.class)
	public void handleInternatlServerError(HttpServletResponse response, RestaurantServerException ex) throws Exception {
		log.error(ex.getMessage());
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@ExceptionHandler(RestaurantNotFoundException.class)
	public void handleNotFound(HttpServletResponse response, RestaurantNotFoundException ex) throws Exception {
		log.error(ex.getMessage());
		response.sendError(HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public void handleConflictException(HttpServletResponse response, DuplicateKeyException ex) throws Exception {
		log.error(ex.getMessage());
		response.sendError(HttpStatus.CONFLICT.value());
	}
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", HttpStatus.BAD_REQUEST);
		body.put("message", "There are empty fields in request");

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);
		return new ResponseEntity<>(body, headers, status);
	}
}