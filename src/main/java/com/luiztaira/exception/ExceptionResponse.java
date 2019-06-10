package com.luiztaira.exception;

import java.util.Date;

public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private int status;
	private String path;
	private String error;
	
	public ExceptionResponse(Date timestamp, String message, int status, String path, String error) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.status = status;
		this.path = path;
		this.error = error;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
	
}