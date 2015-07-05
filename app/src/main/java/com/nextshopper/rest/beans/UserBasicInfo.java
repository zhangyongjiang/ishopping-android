package com.nextshopper.rest.beans;

import java.util.List;

public class UserBasicInfo {
	/**
	 * Required for register request
	 */
	public String firstName;
	
	/**
	 * Required for register request
	 */
	public String lastName;
	
	public String country = "United States";
	public String zipcode;
	public Gender gender;
	public Long	birthday;
	public String imgPath;
	public String about;
	public List<String> phoneList;
}
