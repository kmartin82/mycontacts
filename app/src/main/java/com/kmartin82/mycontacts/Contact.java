package com.kmartin82.mycontacts;


import android.graphics.Bitmap;

import java.util.UUID;

public class Contact {
    private UUID mID;
    private String mName;
    private String mEmail;
    private boolean mFavorite;
    private String mAddress;
    private Bitmap mImage;

    public Contact(){
        mID = UUID.randomUUID();
    }

    public Contact(UUID id){
        mID = id;
    }

    public void setmImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public Bitmap getmImage() {
        return mImage;
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        mFavorite = favorite;
    }

    public void setID(UUID ID) {
        mID = ID;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public UUID getID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getAddress() { return mAddress; }

    public void setAddress(String mAddress) { this.mAddress = mAddress; }
}
