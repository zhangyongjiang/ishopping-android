package com.nextshopper.rest.beans;

public class GenericResponse {
	public String errorMsg = "ok";
	public Integer ivalue;
	public String errorCode;
	
	public GenericResponse() {
	}
	
	public GenericResponse(String msg) {
		this.errorMsg = msg;
	}
}
