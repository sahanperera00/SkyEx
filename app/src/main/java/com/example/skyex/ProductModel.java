package com.example.skyex;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModel implements Parcelable {
    private String productId;
    private String productName;
    private String productDescription;
    private String productType;
    private String productCollection;
    private String productPrice;
    private String productImage;

    public ProductModel() {}

    public ProductModel(String productId, String productName, String productDescription, String productType, String productCollection, String productPrice, String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productType = productType;
        this.productCollection = productCollection;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    protected ProductModel(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        productDescription = in.readString();
        productType = in.readString();
        productCollection = in.readString();
        productPrice = in.readString();
        productImage = in.readString();
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(String productCollection) {
        this.productCollection = productCollection;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productId);
        parcel.writeString(productName);
        parcel.writeString(productDescription);
        parcel.writeString(productType);
        parcel.writeString(productCollection);
        parcel.writeString(productPrice);
        parcel.writeString(productImage);
    }
}
