package com.nextshopper.rest.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class ShippingInfo implements Parcelable {
	public String id;
	public String firstName;
	public String lastName;
	public String phoneNumber;
	public String address;
	public String city;
	public String state;
	public String zipcode;
	public String country;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeString(phoneNumber);
		dest.writeString(address);
		dest.writeString(city);
		dest.writeString(state);
		dest.writeString(zipcode);
		dest.writeString(country);
	}

	public static final Parcelable.Creator<ShippingInfo> CREATOR = new Parcelable.Creator<ShippingInfo>() {
		public ShippingInfo createFromParcel(Parcel in) {
			return new ShippingInfo(in);
		}

		public ShippingInfo[] newArray(int size) {
			return new ShippingInfo[size];
		}
	};

	private ShippingInfo(Parcel in) {
		id = in.readString();
		firstName = in.readString();
		lastName = in.readString();
		phoneNumber = in.readString();
		address = in.readString();
		city = in.readString();
		state = in.readString();
		zipcode = in.readString();
		country = in.readString();
	}

	public ShippingInfo(){
	}
}
