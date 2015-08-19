package com.nextshopper.rest.beans;

import java.util.ArrayList;


public class ProductDetails {
	public Product product = new Product();
	public StoreDetails storeDetails;
	public ArrayList<ProductReviewDetails> reviews;
	public boolean reviewed;
	public boolean liked;

}
