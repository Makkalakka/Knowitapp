package com.example.anton.myknowitapp;

import android.app.Application;

/**
 * Created by Anton on 2016-08-24.
 */
public class PhoneNumber extends Application {

    private String phoneNr;

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneN) {
        this.phoneNr = phoneN;
    }
}