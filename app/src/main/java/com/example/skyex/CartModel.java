package com.example.skyex;

import android.os.Parcel;
import android.os.Parcelable;

public class CartModel implements Parcelable {

    private String Name;
    private String Size;
    private String Quantity;
    private String Price;
    private String ID;
    private String CartImg;

    public CartModel(){}

    public CartModel(String name, String size, String quantity, String price, String ID, String cartImg) {
        Name = name;
        Size = size;
        Quantity = quantity;
        Price = price;
        this.ID = ID;
        CartImg = cartImg;
    }

    protected CartModel(Parcel in) {
        Name = in.readString();
        Size = in.readString();
        Quantity = in.readString();
        Price = in.readString();
        ID = in.readString();
        CartImg = in.readString();
    }

    public static final Creator<CartModel> CREATOR = new Creator<CartModel>() {
        @Override
        public CartModel createFromParcel(Parcel in) {
            return new CartModel(in);
        }

        @Override
        public CartModel[] newArray(int size) {
            return new CartModel[size];
        }
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCartImg() {
        return CartImg;
    }

    public void setCartImg(String cartImg) {
        CartImg = cartImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Size);
        parcel.writeString(Quantity);
        parcel.writeString(Price);
        parcel.writeString(ID);
        parcel.writeString(CartImg);
    }
}
