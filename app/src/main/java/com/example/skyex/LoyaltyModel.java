package com.example.skyex;

import android.os.Parcel;
import android.os.Parcelable;

public class LoyaltyModel implements Parcelable {
    private String Name;
    private String Nic;
    private String Email;
    private String PhoneNo;
    private String points;

    public LoyaltyModel() {}

    public LoyaltyModel(String name, String nic, String email, String phoneNo, String points) {
        Name = name;
        Nic = nic;
        Email = email;
        PhoneNo = phoneNo;
        this.points = points;
    }

    protected LoyaltyModel(Parcel in) {
        Name = in.readString();
        Nic = in.readString();
        Email = in.readString();
        PhoneNo = in.readString();
        points = in.readString();
    }

    public static final Creator<LoyaltyModel> CREATOR = new Creator<LoyaltyModel>() {
        @Override
        public LoyaltyModel createFromParcel(Parcel in) {
            return new LoyaltyModel(in);
        }

        @Override
        public LoyaltyModel[] newArray(int size) {
            return new LoyaltyModel[size];
        }
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNic() {
        return Nic;
    }

    public void setNic(String nic) {
        Nic = nic;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Nic);
        parcel.writeString(Email);
        parcel.writeString(PhoneNo);
        parcel.writeString(points);
    }
}
