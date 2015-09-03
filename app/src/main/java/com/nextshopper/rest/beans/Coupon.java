package com.nextshopper.rest.beans;

import java.util.Map;

public class Coupon {
	public String userId;
	public String storeId;
	public String productId;
	public String couponCode;
	public CouponType type;
	public String channel;
	public long start;
	public long end;
	public int quota;
	public int used;
	public Map<String, String> settings;
    public String id;
    public int version;
    public long created;
    public long updated;
}
