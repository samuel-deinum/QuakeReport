package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {


    public WordAdapter(Activity context, ArrayList<Word> words) {
        super(context, 0, words);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        //Just setting our input to some useful variables. current word is a function of position -----------
        Word currentWord = getItem(position);
        View listItemView = convertView;

        //--------List item view has to be null for it to work, if not the app will crash. ------------------
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView magView = (TextView) listItemView.findViewById(R.id.mag_Text);
        magView.setText(currentWord.getmMagnitude());

        TextView locationView = (TextView) listItemView.findViewById(R.id.location_Text);
        locationView.setText(currentWord.getmLocation());

        TextView dateView = (TextView) listItemView.findViewById(R.id.date_Text);
        dateView.setText(currentWord.getmDate());

        return listItemView;
    }
}
