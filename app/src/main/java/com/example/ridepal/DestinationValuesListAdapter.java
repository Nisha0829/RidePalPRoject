package com.example.ridepal;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class DestinationValuesListAdapter extends ArrayAdapter<DestinationValues> {

    private Context mContext;
    private int mResource;

    public DestinationValuesListAdapter( Context context, int resource,  DestinationValues[] objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = getItem(position).getName();
        String origin = getItem(position).getOrigin();
        String destination = getItem(position).getDestination();
        return super.getView(position, convertView, parent);
    }
}
