package com.nextshopper.rest.beans;

import java.util.ArrayList;
import java.util.List;


public class ProductCategory {
	public String name;
	public List<String> targetCustomerTags;
	public List<Option> options;
	public List<ProductCategory> subcategories;
	
}
