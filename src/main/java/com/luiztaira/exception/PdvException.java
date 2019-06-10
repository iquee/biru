package com.luiztaira.exception;

public class PdvException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public PdvException() {		
	}
	
	public PdvException(String msg) {
		super(msg);
	}

}
