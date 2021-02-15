package com.example.android.quakereport;

import android.location.Location;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Earthquake {

    private Double mMagnitude;
    private String mLocation;
    private Long mDate;
    private String mURL;


    public Earthquake(Double Magnitude, String Location, Long Date, String URL){

        mMagnitude = Magnitude;
        mLocation = Location;
        mDate = Date;
        mURL = URL;

    }

    public Double getmMagnitude(){
        return mMagnitude;
    };

    public String getmLocation(){
        return mLocation;
    }

    public Long getmDate(){
        return mDate;
    }

    public String getmURL(){
        return mURL;
     }

}
