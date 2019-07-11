package com.luiztaira.web.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luiztaira.exception.PdvNotFoundException;
import com.luiztaira.exception.PdvServerException;


@RestControllerAdvice
public class PdvControllerAdvice {
	
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
	
}