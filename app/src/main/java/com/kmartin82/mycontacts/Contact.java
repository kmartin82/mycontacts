package com.kmartin82.mycontacts;


import java.util.UUID;

public class Contact {
    private UUID mID;
    private String mname;
    private String memail;
    private boolean mFavorite;

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        mFavorite = favorite;
    }

    public void setID(UUID ID) {
        mID = ID;
    }

    public Contact(){
        mID = UUID.randomUUID();
    }


    public void setMname(String mname) {
        mname = mname;
    }

    public void setMemail(String memail) {
        memail = memail;
    }

    public UUID getID() {
        return mID;
    }

    public String getMname() {
        return mname;
    }

    public String getMemail() {
        return memail;
    }
}
