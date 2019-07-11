package com.luiztaira.web.rest;

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

import com.luiztaira.exception.PdvNotFoundException;
import com.luiztaira.exception.PdvServerException;


@RestControllerAdvice
public class PdvControllerAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(PdvServerException.class)
	public void handleInternatlServerError(HttpServletResponse response) throws Exception {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@ExceptionHandler(PdvNotFoundException.class)
	public void handleNotFound(HttpServletResponse response) throws Exception {
		response.sendError(HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public void handleConflictException(HttpServletResponse response) throws Exception {		
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