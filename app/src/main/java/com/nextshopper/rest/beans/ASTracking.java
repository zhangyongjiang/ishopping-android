package com.nextshopper.rest.beans;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Define a Tracking element
 * Created by User on 11/6/14
 */
public class ASTracking {

    /**Identifier of the tracking in the Aftership system*/
    public String id;

    /**Tracking number of a shipment. Duplicate tracking numbers, or tracking number with invalid tracking
     * number format will not be accepted. */
    public String trackingNumber;

    /**Unique code of each courier. If you do not specify a slug, Aftership will automatically detect
     * the courier based on the tracking number format and your selected couriers*/
    public String slug;

    /** Email address(es) to receive email notifications. Use comma for multiple emails. */
    public List<String> emails;

    /** Phone number(s) to receive sms notifications. Use comma for multiple emails.
     * Enter + area code before phone number. */
    public List<String> smses;

    /** Title of the tracking. Default value as trackingNumber */
    public String title;

    /** Customer name of the tracking. */
    public String customerName;

    /** ISO Alpha-3(three letters)to specify the destination of the shipment.
     * If you use postal service to send international shipments, AfterShip will automatically
     * get tracking results at destination courier as well (e.g. USPS for USA). */
    public ISO3Country destinationCountryISO3;

    /**  Origin country of the tracking. ISO Alpha-3 */
    public ISO3Country originCountryISO3;

    /** Text field for order ID */
    public String orderID;

    /** Text field for order path */
    public String orderIDPath;

    /** Custom fields that accept any TEXT STRING*/
    public Map<String, String> customFields;

    /** fields informed by Aftership API*/

    /**  Date and time of the tracking created. */
    public Date createdAt;

    /** Date and time of the tracking last updated. */
    public Date updatedAt;

    /** Whether or not AfterShip will continue tracking the shipments.
     * Value is `false` when status is `Delivered` or `Expired`. */
    public boolean active;

    /** Expected delivery date (if any). */
    public String expectedDelivery;

    /**  Number	Number of packages under the tracking. */
    public int shipmentPackageCount;

    /** Shipment type provided by carrier (if any). */
    public String shipmentType;

    /** Signed by information for delivered shipment (if any). */
    public String signedBy;

    /**  Source of how this tracking is added.  */
    public String source;

    /** Current status of tracking. */
    public StatusTag tag;

    /**  Number of attempts AfterShip tracks at courier's system. */
    public int trackedCount;

    /** Array of Hash describes the checkpoint information. */
    List<Checkpoint> checkpoints;

    /**Unique Token*/
    public String uniqueToken;

    /**Tracking Account number tracking_account_number*/
    public String trackingAccountNumber;

    /**Tracking postal code tracking_postal_code*/
    public String trackingPostalCode;

    /**Tracking ship date tracking_ship_date*/
    public String trackingShipDate;

}


