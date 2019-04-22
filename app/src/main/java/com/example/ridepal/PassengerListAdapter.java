package com.example.ridepal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;




public class PassengerListAdapter extends ArrayAdapter<PassengerTestObject>{

    private Context mContext;
    private int mResource;


    public PassengerListAdapter(Context context, int resource, ArrayList<PassengerTestObject> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        String name = getItem(position).getName();
        String picture = getItem(position).getPicture();
        String origin = getItem(position).getOriginName();
        String desination = getItem(position).getDestName();
        String emailID = getItem(position).getEmailID();
        LatLng distlatLng = getItem(position).getOriginLatLng(); //getLatLng
        LatLng originlatLng = getItem(position).getDestLatLng();

        PassengerTestObject passenger = new PassengerTestObject(emailID,name,picture,desination,origin, originlatLng, distlatLng);

        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView)convertView.findViewById(R.id.passengername);
        TextView tvOrigin = (TextView)convertView.findViewById(R.id.passengerorigin);
        TextView tvDesination = (TextView)convertView.findViewById(R.id.passengerdestination);

        tvName.setText(name);
        tvOrigin.setText("Origin: "+origin);
        tvDesination.setText("Destination: "+desination);

        return convertView;



    }

}
