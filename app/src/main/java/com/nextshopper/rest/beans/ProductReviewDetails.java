package com.nextshopper.rest.beans;


import android.os.Parcel;
import android.os.Parcelable;

public class ProductReviewDetails implements Parcelable{
	public ProductReview review;
	public Product product;
	public StoreBasicInfo storeInfo;
	public UserBasicInfo user;
	public Boolean purchased;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeParcelable(review, flags);
		dest.writeParcelable(user, flags);
	}

	public static final Parcelable.Creator<ProductReviewDetails> CREATOR = new Parcelable.Creator<ProductReviewDetails>() {
		public ProductReviewDetails createFromParcel(Parcel in) {
			return new ProductReviewDetails(in);
		}

		public ProductReviewDetails[] newArray(int size) {
			return new ProductReviewDetails[size];
		}
	};

	private ProductReviewDetails(Parcel in) {
		review = (ProductReview)in.readParcelable(ProductReview.class.getClassLoader());
		user = in.readParcelable(UserBasicInfo.class.getClassLoader());
	}
	public ProductReviewDetails(){}
}
