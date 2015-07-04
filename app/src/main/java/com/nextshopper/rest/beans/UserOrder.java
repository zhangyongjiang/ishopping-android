package com.nextshopper.rest.beans;

public class UserOrder { 
	public String orderNumber;
	public String userId;
	public String storeId;
	public OrderStatus status;
	public float subtotal;
	public float shippingCost;
	public float total;
	public ShippingInfo shipping;
	public CreditCardBillingInfo billingInfo;
	public BraintreeBillingInfo braintreeInfo;
	public Tracking tracking;
	public String transactionId;
	public String option;
	public List sellerNotes;
	public float commissionPercent;
	public List promotionList;
	public float promoValue;
	public String id;
	public String json;
	public int version;
	public long created;
	public long updated;
} 
