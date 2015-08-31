package com.nextshopper.rest.beans;

import java.util.Map;

public class OrderItem {
	public String orderId;
	public String userId;
	public String productId;
	public float listPrice;
	public float salePrice;
	public String option;
	public int quantity;
	public OrderItemNote sellerNote;
	public String productName;
	public String productImage;
	public String id;
	public int version;
	public long created;
	public long updated;
    public String skuId;
    public Map<String, String> options;
}
