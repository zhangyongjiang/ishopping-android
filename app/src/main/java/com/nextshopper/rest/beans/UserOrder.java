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
	public java.util.List sellerNotes;
	public float commissionPercent;
	public java.util.List promotionList;
	public String id;
	public int version;
	public long created;
	public long updated;
    public float creditUsed;
    public float creditEarned;
    public float couponValue;
    public float couponValueRefund;
    public String couponCode;

} 
