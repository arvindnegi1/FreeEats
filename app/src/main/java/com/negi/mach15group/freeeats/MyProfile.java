package com.negi.mach15group.freeeats;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MyProfile extends Fragment {

Button logout;
TextView phone,name,loct;
    managesession ms;
    Double latitude,longitude;
    String address;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_profile, container, false);
    //phone=v.findViewById(R.id.phone);
        phone=v.findViewById(R.id.profilephone);
        name=v.findViewById(R.id.username);
    loct=v.findViewById(R.id.loc);
       GPSTracker gpsTracker = new GPSTracker(getContext());
        if (gpsTracker.canGetLocation()) {

            if (gpsTracker == null) {
                Toast.makeText(getContext(), "gpstracker error", Toast.LENGTH_SHORT).show();
            } else {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                setDefaultAddrss(latitude, longitude);
            }
            loct.setText(address);

        } else {
            Toast.makeText(getContext(), "Can't Fetch Location", Toast.LENGTH_SHORT).show();

        }
  ms=new managesession(this.getContext());
  try {
       phone.setText(ms.getPhone());
       name.setText(ms.getname());
   }
   catch (Exception e)
   {
       e.printStackTrace();
       Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
   }


      // phone.setText(sharedPreferences.getString("email",null));
    /*logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ms.logoutUser();
        }
    });*/
    return v;
    }

    private void setDefaultAddrss(double latitude, double longitude) {
        Geocoder geocoder;

        List<Address> addresses = null;
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
