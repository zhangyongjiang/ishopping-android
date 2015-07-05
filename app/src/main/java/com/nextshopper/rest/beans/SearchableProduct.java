package com.nextshopper.rest.beans;

import java.util.List;

public class SearchableProduct {
	public String id;
	public String storeId;
	public List<String> tags;
	public String name;
	public String status;
	public String description;
	public String manufacture;
	public List<String> imgUrl;
	public String model;
	public int likes;
	public int reviews;
	public float rating;
	public float price;
	public Float listPrice;
	public String country;
	public Long created;
	public Boolean liked;
	public float score;
	public int ranking;
	public List<String> recommendation;
	public List<String> crossind;
	
	public SearchableProduct() {
	}
	
	public SearchableProduct(Product prod) {
		id = prod.id;
		description = prod.description;
		imgUrl = prod.imgs;
		manufacture = prod.manufacture;
		name = prod.name;
		if(prod.reviews>0)rating = prod.totalRating / prod.reviews;
		reviews = prod.reviews;
		likes = prod.likes;
		status = prod.status.name();
		storeId = prod.storeId;
		tags = prod.tags;
		created = prod.created;
		price = prod.salePrice;
		listPrice = (float)prod.listPrice;
		ranking = prod.ranking;
	}
}
