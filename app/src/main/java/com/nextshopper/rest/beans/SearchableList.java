package com.nextshopper.rest.beans;

import java.util.ArrayList;
import java.util.List;

public class SearchableList<T> {
	public int total;
	public List<T> items = new ArrayList<T>();
}
