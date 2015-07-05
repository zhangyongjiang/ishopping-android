package com.nextshopper.rest.beans;

import java.util.List;





public class SplitOrder {
	public UserOrder order;
	public List<OrderItem> items;
	public List<Product> products;
	public UserBasicInfo user;
}
