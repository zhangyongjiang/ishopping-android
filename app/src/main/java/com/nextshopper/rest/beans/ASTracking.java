package com.nextshopper.rest.beans;

public class ASTracking { 
	public String id;
	public String trackingNumber;
	public String slug;
	public java.util.List emails;
	public java.util.List smses;
	public String title;
	public String customerName;
	public ISO3Country destinationCountryISO3;
	public ISO3Country originCountryISO3;
	public String orderID;
	public String orderIDPath;
	public java.util.Map<java.lang.String, java.lang.String> customFields;
	public java.util.Date createdAt;
	public java.util.Date updatedAt;
	public boolean active;
	public String expectedDelivery;
	public int shipmentPackageCount;
	public String shipmentType;
	public String signedBy;
	public String source;
	public StatusTag tag;
	public int trackedCount;
	public java.util.List checkpoints;
	public String uniqueToken;
	public String trackingAccountNumber;
	public String trackingPostalCode;
	public String trackingShipDate;
} 
