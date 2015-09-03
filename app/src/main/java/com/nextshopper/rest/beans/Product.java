package com.nextshopper.rest.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Product {
	public java.util.List<String> imgs = new ArrayList<String>();
	public String name;
	public String description;
    public List<NameValue> specs;
	public String manufacture;
	public java.util.List tags;
	public java.util.List targetCustomers;
	public int likes;
	public int reviews;
	public int repliedReviews;
	public int totalRating;
	public String storeId;
	public int quantity;
    public String quantityUnit;
    public String shipping;
    public float shippingCost;
	public float listPrice;
	public float salePrice;
	public ProductStatus status;
	public int flagged;
	public java.util.List attachments;
	public String options;
	public int ranking;
	public String id;
	public int version;
	public long created;
	public long updated;
	public String storeName;
    public String buyUrl;
    public String aid;
    public String level1Category;
    public String level2Category;
    public String level3Category;
    public Map<String, Option> prodOptions;
    public List<ProductSku> skuPriceAndQuantity;
}
