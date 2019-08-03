package com.luiztaira.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RestaurantServerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public RestaurantServerException() {		
	}
	
	public RestaurantServerException(String msg) {
		super(msg);
	}

}
