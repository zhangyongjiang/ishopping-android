package com.nextshopper.rest.beans;







public class StoreDetails {
	public String id;
	public StoreSummary summary;
	public boolean followed;
	
	// fields below should be returned only for authorized user
	public User owner;
	public Account ownerEmail;
	public Store store;	
	public StoreSalesSummary salesSummary;
}
