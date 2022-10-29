package com.example.skyex;

import android.os.Parcel;
import android.os.Parcelable;

public class CardModel implements Parcelable {
    private String CardNumber;
    private String month;
    private String year;
    private String name;
    private String ID;

    public CardModel() {
    }


    protected CardModel(Parcel in) {
        CardNumber = in.readString();
        month = in.readString();
        year = in.readString();
        name = in.readString();
        ID = in.readString();
    }

    public static final Creator<CardModel> CREATOR = new Creator<CardModel>() {
        @Override
        public CardModel createFromParcel(Parcel in) {
            return new CardModel(in);
        }

        @Override
        public CardModel[] newArray(int size) {
            return new CardModel[size];
        }
    };

    public CardModel(String CardNumber, String month, String year,  String name, String ID) {
        this.CardNumber = CardNumber;
        this.month = month;
        this.year = year;
        this.name = name;
        this.ID = ID;
    }


    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String CardNumber) {
        this.CardNumber = CardNumber;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(CardNumber);
        parcel.writeString(month);
        parcel.writeString(year);
        parcel.writeString(name);
        parcel.writeString(ID);
    }
}
