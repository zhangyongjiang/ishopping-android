package com.nextshopper.rest.beans;

import java.io.IOException;


public enum OrderLogType {
	ReturnedByUser(ReturnRequest.class),
	RefundSubmitted(RefundRequest.class),
	CancelRequest(CancelRequest.class),
	AddTracking(Tracking.class),
	Memo(Memo.class),
	Charged(BillingTransaction.class)
	;
	private Class clazz;
	private OrderLogType(Class clazz) {
		this.clazz = clazz;
	}

	public static OrderLogType detectType(Class clazz) {
		for(OrderLogType type : OrderLogType.class.getEnumConstants()) {
			if(clazz.equals(type.clazz))
				return type;
		}
		throw new RuntimeException("cannot get OrderLogType for class " + clazz);
	}
}
