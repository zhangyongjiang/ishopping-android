package com.nextshopper.rest.beans;

import java.util.List;

public class UserOrderDetails {
	public UserOrder order;
	public UserBasicInfo user;
	public StoreBasicInfo store;
	public List<OrderItemDetails> items;
}
