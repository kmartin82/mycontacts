package com.kmartin82.mycontacts;


import java.util.UUID;

public class Contact {
    private UUID mID;
    private String mname;
    private String memail;

    public Contact(){
        mID = UUID.randomUUID();
    }


    public void setMname(String mname) {
        this.mname = mname;
    }

    public void setMemail(String memail) {
        this.memail = memail;
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
