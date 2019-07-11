package com.luiztaira.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PdvServerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public PdvServerException() {		
	}
	
	public PdvServerException(String msg) {
		super(msg);
	}

}
