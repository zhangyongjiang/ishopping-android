package com.nextshopper.rest.beans;

public enum MoneyFlowType {
	UserOrder(true), 
	Commission(false), 
	UserOrderRefund(false), 
	CommissionRefund(true), 
	Withdraw(false),
	WithdrawFee(false)
	;
	
	private boolean plus;
	private MoneyFlowType(boolean plus) {
		this.plus = plus;
	}
	
	public boolean isPlus() {
		return plus;
	}
}
