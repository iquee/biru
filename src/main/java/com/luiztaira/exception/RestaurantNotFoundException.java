package com.luiztaira.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestaurantNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RestaurantNotFoundException() {
		super();
	}

	public RestaurantNotFoundException(String msg) {
		super(msg);
	}

	public RestaurantNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}