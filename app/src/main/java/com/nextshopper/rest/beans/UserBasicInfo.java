package com.nextshopper.rest.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserBasicInfo implements Parcelable{
	/**
	 * Required for register request
	 */
	public String firstName;
	
	/**
	 * Required for register request
	 */
	public String lastName;
	
	public String country = "United States";
	public String zipcode;
	public Gender gender;
	public Long	birthday;
	public String imgPath;
	public String about;
	public List<String> phoneList;
	public String pwd;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeString(imgPath);
	}
	public static final Parcelable.Creator<UserBasicInfo> CREATOR = new Parcelable.Creator<UserBasicInfo>() {
		public UserBasicInfo createFromParcel(Parcel in) {
			return new UserBasicInfo(in);
		}

		public UserBasicInfo[] newArray(int size) {
			return new UserBasicInfo[size];
		}
	};

	private UserBasicInfo(Parcel in) {
		firstName = in.readString();
		lastName = in.readString();
		imgPath = in.readString();
	}
	public UserBasicInfo(){}
}
