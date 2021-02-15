package com.example.android.quakereport;

/**
 * Created by sam on 02/02/2018.
 */

public class Word {
    private String mMagnitude;
    private String mLocation;
    private String mDate;


    public Word(String Magnitude, String Location, String Date){

        mMagnitude = Magnitude;
        mLocation = Location;
        mDate = Date;

    }

    public String getmMagnitude(){
        return mMagnitude;
    };

    public String getmLocation(){
        return mLocation;
    }

    public String getmDate(){
        return mDate;
    }


}
