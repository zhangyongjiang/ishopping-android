package com.nextshopper.rest.beans;

import java.util.List;

public class ListWrapper<T> {
	public List<T> items;
	public int total;
	
	public ListWrapper(List<T> items) {
		this.items = items;
	}
	
}
