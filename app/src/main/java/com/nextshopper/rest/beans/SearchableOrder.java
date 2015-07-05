package com.nextshopper.rest.beans;

import java.util.ArrayList;
import java.util.List;




public class SearchableOrder {
	public String id;
	public String orderNumber;
	public String storeId;
	public List<String> productId;
	public String userId;
	public OrderStatus status;
	public long created;
	public long updated;
	
	public SearchableOrder() {
	}
	
	public SearchableOrder(UserOrder order, List<OrderItem> items) {
		id = order.id;
		storeId = order.storeId;
		userId = order.userId;
		productId = new ArrayList<String>();
		for(OrderItem item : items) {
			productId.add(item.productId);
		}
		status = order.status;
		created = order.created;
		updated = order.updated;
		orderNumber = order.orderNumber;
	}
}
