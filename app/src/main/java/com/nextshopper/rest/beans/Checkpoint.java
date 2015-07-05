package com.nextshopper.rest.beans;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 10/6/14.
 */
public class Checkpoint {

    /** Date and time of the tracking created. */
    public String createdAt;

    /** Date and time of the checkpoint, provided by courier. Value may be:
     Empty String,
     YYYY-MM-DD,
     YYYY-MM-DDTHH:MM:SS, or
     YYYY-MM-DDTHH:MM:SS+TIMEZONE */
    public String checkpointTime;

    /** Location info (if any) */
    public String city;

    /** Country ISO Alpha-3 (three letters) of the checkpoint */
    public ISO3Country countryISO3;

    /** Country name of the checkpoint, may also contain other location info. */
    public String countryName;

    /** Checkpoint message */
    public String message;

    /** Location info (if any) */
    public String state;

    /** Status of the checkpoint */
    public String tag;

    /** Location info (if any) */
    public String zip;

}



