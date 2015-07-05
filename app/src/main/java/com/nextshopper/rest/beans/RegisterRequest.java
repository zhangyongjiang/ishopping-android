package com.nextshopper.rest.beans;


public class RegisterRequest extends UserBasicInfo {
	/**
	 * Required for register request
	 */
	public String email;
	
	/**
	 * Required for register request
	 */
	public String password;
	
	/**
	 * Required for register request. User ROBERT for Android and DRAGON for iphone
	 */
	public String invitationCode;
	
	public String ip;
}
