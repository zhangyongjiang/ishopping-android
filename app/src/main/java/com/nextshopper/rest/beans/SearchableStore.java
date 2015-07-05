package com.nextshopper.rest.beans;

import java.util.List;



public class SearchableStore {
	public String id;
	public String ownerId;
	public List<String> images;
	public String logo;
	public StoreStatus status;
	public String summary;
	public String name;
	public String city;
	public String state;
	public String country;
	public Long created;
	public String phone;
	
	public SearchableStore() {
	}
	
	public SearchableStore(Store store) {
		id = store.id;
		summary = store.info.summary;
		images = store.info.images;
		status = store.status;
		city = store.info.address.city;
		state = store.info.address.state;
		country = store.info.address.country;
		created = store.created;
		name = store.info.name;
		logo = store.info.logo;
		phone = store.info.phone;
	}
}
