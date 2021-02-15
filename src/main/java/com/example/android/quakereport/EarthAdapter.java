package com.example.android.quakereport;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;
import android.graphics.drawable.GradientDrawable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.support.v4.content.ContextCompat;

/**
 * Created by sam on 12/03/2018.
 */

public class EarthAdapter extends ArrayAdapter<Earthquake> {
    public EarthAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    private static final String LOCATION_SEPERATOR = " of ";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        //Just setting our input to some useful variables. current word is a function of position -----------
        Earthquake currentEarth = getItem(position);
        View listItemView = convertView;

        //--------List item view has to be null for it to work, if not the app will crash. ------------------
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //___________________________Get DATE and TIME______________________________________________

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        Long date = currentEarth.getmDate();
        Date dateObject = new Date(date);
        String earth_date = dateFormat.format(dateObject);
        String earth_time = timeFormat.format(dateObject);

        //__________________________SPLIT UP THE LOCATION __________________________________________

        String baselocation = currentEarth.getmLocation();
        String location_pointer;
        String location;


        if (baselocation.contains(LOCATION_SEPERATOR)){
            String[] splitlocation = baselocation.split(LOCATION_SEPERATOR);
            location_pointer = splitlocation[0] + LOCATION_SEPERATOR;
            location = splitlocation[1];
        }else{
            location_pointer = "Near the ";
            location = baselocation;
        }


        //___________________________CLEAN UP THE DECIMAL IN MAG____________________________________

        DecimalFormat Dformatter = new DecimalFormat("0.0");
        String magnitude = Dformatter.format(currentEarth.getmMagnitude());



        //___________________________SET THE TEXT TO THE TEXTVIEWS__________________________________

        TextView magView = (TextView) listItemView.findViewById(R.id.mag_Text);
        magView.setText(magnitude);

        TextView location_pointerView = (TextView)  listItemView.findViewById(R.id.location_pointer_Text);
        location_pointerView.setText(location_pointer);

        TextView locationView = (TextView) listItemView.findViewById(R.id.location_Text);
        locationView.setText(location);

        TextView dateView = (TextView) listItemView.findViewById(R.id.date_Text);
        dateView.setText(earth_date);

        TextView timeView = (TextView) listItemView.findViewById((R.id.time_Text));
        timeView.setText(earth_time);

        //________________________________SET THE COLOR_____________________________________________

        GradientDrawable circle_color = (GradientDrawable) magView.getBackground();
        int magnitudecolor = getMagnitudecolor(currentEarth.getmMagnitude());
        circle_color.setColor(magnitudecolor);
        //__________________________________________________________________________________________


        return listItemView;
    }



private int getMagnitudecolor(double magnitude){

    int magnitudeColorResourceId;
    int magnitudeFloor = (int) Math.floor(magnitude);
    switch (magnitudeFloor){
        case 0:
        case 1:
            magnitudeColorResourceId = R.color.magnitude1;
            break;
        case 2:
            magnitudeColorResourceId = R.color.magnitude2;
            break;
        case 3:
            magnitudeColorResourceId = R.color.magnitude3;
            break;
        case 4:
            magnitudeColorResourceId = R.color.magnitude4;
            break;
        case 5:
            magnitudeColorResourceId = R.color.magnitude5;
            break;
        case 6:
            magnitudeColorResourceId = R.color.magnitude6;
            break;
        case 7:
            magnitudeColorResourceId = R.color.magnitude7;
            break;
        case 8:
            magnitudeColorResourceId = R.color.magnitude8;
            break;
        case 9:
            magnitudeColorResourceId = R.color.magnitude9;
            break;
        default:
            magnitudeColorResourceId = R.color.magnitude10plus;
            break;
    }
    return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}