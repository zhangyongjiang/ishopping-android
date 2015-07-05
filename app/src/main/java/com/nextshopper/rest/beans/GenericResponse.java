package com.nextshopper.rest.beans;

public class GenericResponse {
	public String msg = "ok";
	public Integer ivalue;
	
	public GenericResponse() {
	}
	
	public GenericResponse(String msg) {
		this.msg = msg;
	}
}
