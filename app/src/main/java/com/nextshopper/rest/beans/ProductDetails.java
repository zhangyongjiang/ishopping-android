package com.nextshopper.rest.beans;

import java.util.List;


public class ProductDetails {
	public Product product = new Product();
	public StoreDetails storeDetails;
	public List<ProductReviewDetails> reviews;
	public boolean reviewed;
	public boolean liked;
}
