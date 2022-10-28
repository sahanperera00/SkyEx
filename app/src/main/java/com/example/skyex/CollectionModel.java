package com.example.skyex;

import android.os.Parcel;
import android.os.Parcelable;

public class CollectionModel implements Parcelable {
    private String collectionId;
    private String collectionName;
    private String collectionImage;

    public CollectionModel() {}

    public CollectionModel(String collectionId, String collectionName, String collectionImage) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.collectionImage = collectionImage;
    }

    protected CollectionModel(Parcel in) {
        collectionId = in.readString();
        collectionName = in.readString();
        collectionImage = in.readString();
    }

    public static final Creator<CollectionModel> CREATOR = new Creator<CollectionModel>() {
        @Override
        public CollectionModel createFromParcel(Parcel in) {
            return new CollectionModel(in);
        }

        @Override
        public CollectionModel[] newArray(int size) {
            return new CollectionModel[size];
        }
    };

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionImage() {
        return collectionImage;
    }

    public void setCollectionImage(String collectionImage) {
        this.collectionImage = collectionImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(collectionId);
        parcel.writeString(collectionName);
        parcel.writeString(collectionImage);
    }
}
