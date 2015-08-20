package com.nextshopper.rest.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductReview implements Parcelable{
	public String productId;
	public String storeId;
	public String userId;
	public int rating;
	public String comment;
	public Integer serviceRating;
	public java.util.List imgPath;
	public SellerCommentOnProductReview sellerComment;
	public String id;
	public int version;
	public long created;
	public long updated;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rating);
        dest.writeString(comment);
        dest.writeLong(created);
    }

    public static final Parcelable.Creator<ProductReview> CREATOR = new Parcelable.Creator<ProductReview>() {
        public ProductReview createFromParcel(Parcel in) {
            return new ProductReview(in);
        }

        public ProductReview[] newArray(int size) {
            return new ProductReview[size];
        }
    };

    private ProductReview(Parcel in) {
        rating = in.readInt();
        comment = in.readString();
        created = in.readLong();
    }

    public ProductReview(){}
}
