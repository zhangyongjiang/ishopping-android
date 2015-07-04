package com.nextshopper.rest.beans;

public class ASTracking { 
	public String id;
	public String trackingNumber;
	public String slug;
	public List emails;
	public List smses;
	public String title;
	public String customerName;
	public ISO3Country destinationCountryISO3;
	public ISO3Country originCountryISO3;
	public String orderID;
	public String orderIDPath;
	public Map customFields;
	public Date createdAt;
	public Date updatedAt;
	public boolean active;
	public String expectedDelivery;
	public int shipmentPackageCount;
	public String shipmentType;
	public String signedBy;
	public String source;
	public StatusTag tag;
	public int trackedCount;
	public List checkpoints;
	public String uniqueToken;
	public String trackingAccountNumber;
	public String trackingPostalCode;
	public String trackingShipDate;
} 
