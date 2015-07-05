package com.nextshopper.rest.beans;

import java.util.ArrayList;
import java.util.List;

public class ShippingCountries {
	private static List<String> countries = new ArrayList<String>(){{
        add("Argentina");
        add("Australia");
        add("Austria");
        add("Belarus");
        add("Belgium");
        add("Brazil");
        add("Bulgaria");
        add("Canada");
        add("Chile");
        add("China");
        add("Colombia");
        add("Costa Rica");
        add("Croatia");
        add("Czech Republic");
        add("Denmark");
        add("Ecuador");
        add("Egypt");
        add("Estonia");
        add("Finland");
        add("France");
        add("Germany");
        add("Greece");
        add("Hong Kong");
        add("Hungary");
        add("India");
        add("Indonesia");
        add("Ireland");
        add("Israel");
        add("Italy");
        add("Jamaica");
        add("Japan");
        add("Kuwait");
        add("Latvia");
        add("Lithuania");
        add("Malaysia");
        add("Mexico");
        add("Netherlands");
        add("New Zealand");
        add("Norway");
        add("Pakistan");
        add("Peru");
        add("Philippines");
        add("Poland");
        add("Portugal");
        add("Puerto Rico");
        add("Romania");
        add("Russia");
        add("Saudi Arabia");
        add("Singapore");
        add("Slovakia");
        add("Slovenia");
        add("South Africa");
        add("South Korea");
        add("Spain");
        add("Sweden");
        add("Switzerland");
        add("Thailand");
        add("Turkey");
        add("Ukraine");
        add("United Arab Emirates");
        add("United Kingdom (Great Britain)");
        add("United States");
        add("United States Virgin Islands");
        add("Venezuela");
        add("Vietnam");
	}};
	
	public static boolean contains(String country) {
		return countries.contains(country);
	}
}
