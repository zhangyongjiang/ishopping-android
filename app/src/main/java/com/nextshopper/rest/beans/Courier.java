package com.nextshopper.rest.beans;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Define a Courier
 * Created by User on 10/6/14.
 */
public class Courier {

    /** Unique code of courier */
    public String slug;
    /** Name of courier */
    public String name;
    /** Contact phone number of courier */
    public String phone;
    /** Other name of courier, if several they will be separated by commas */
    public String other_name;
    /** Website link of courier */
    public String web_url;
    /** Require fields for this courier */
    public List<String> requireFields;

}
