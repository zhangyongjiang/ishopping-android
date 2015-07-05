package com.nextshopper.rest.beans;

import java.util.ArrayList;
import java.util.List;

public class BillingTransaction {
	public String transactionId;
	public float amount;
	public String memo;
	public List<BillingTransaction> refunds;
	
	public BillingTransaction addRefund(String transactionId, float amount) {
		if(refunds == null) 
			refunds = new ArrayList<BillingTransaction>();
		BillingTransaction bt = new BillingTransaction();
		bt.transactionId = transactionId;
		bt.amount = amount;
		refunds.add(bt);
		return bt;
	}
	
	public float amountRefunded() {
		if(refunds == null) return 0.0f;
		
		float total = 0f;
		for(BillingTransaction bt : refunds) {
			total += bt.amount;
		}
		return total;
	}
	
	public float netAmountCharged() {
		return amount - amountRefunded();
	}
}
