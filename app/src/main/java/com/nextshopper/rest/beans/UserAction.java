package com.nextshopper.rest.beans;

public enum UserAction {
	Purchase,
	AddToCart,
	Liked,
	ViewedProduct,
	ViewedCategory,
	SearchedKeywords,
	;
	
	public boolean isProductAction() {
		return this.equals(Purchase) || this.equals(AddToCart) || this.equals(Liked) || this.equals(ViewedProduct); 
	}
	
	public boolean isCategoryAction() {
		return this.equals(ViewedCategory); 
	}
}
