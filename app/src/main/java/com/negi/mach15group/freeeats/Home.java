package com.negi.mach15group.freeeats;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Home extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private double latitude, longitude;

    private LatLng currentLatLong;
RecyclerView recyclerView;
ProductAdapter adapter;
List<Item> itemList;
FirebaseDatabase firebaseDatabase;
DatabaseReference mref;

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        // Inflate the layout for this fragment
                itemList=new ArrayList<>();

                firebaseDatabase=FirebaseDatabase.getInstance();
                mref=firebaseDatabase.getReference("User");
      recyclerView=(RecyclerView)view.findViewById(R.id.recycle);
      RecyclerView.ItemAnimator itemAnimator=new DefaultItemAnimator();
      itemAnimator.setAddDuration(1000);
      itemAnimator.setRemoveDuration(1000);
      recyclerView.setItemAnimator(itemAnimator);

      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


GPSTracker gpsTracker;
gpsTracker=new GPSTracker(getContext());
final double curlat,curlon;
final Location location;
curlat=gpsTracker.getLatitude();
curlon=gpsTracker.getLongitude();
location=gpsTracker.getLocation();
     mref.addValueEventListener(new ValueEventListener() {


         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot ds:dataSnapshot.getChildren())
            {  Double endlat=Double.parseDouble(String.valueOf(ds.child("lat").getValue()));
                   Double endlon= Double.parseDouble(String.valueOf(ds.child("lon").getValue()));
               float result[]=new float[10];
                   location.distanceBetween(curlat,curlon,endlat,endlon,result);
                if(result[0]<1000) {
                    Item item = new Item(ds.child("event_name").getValue().toString(),
                            ds.child("item").getValue().toString(),
                            ds.child("start_time").getValue().toString(),
                            ds.child("stop_time").getValue().toString(),
                            "free",
                            R.drawable.logoeats,
                            endlat, endlon
                    );
                    itemList.add(item);
                }
            }
             adapter=new ProductAdapter(getContext(),itemList);

             recyclerView.setAdapter(adapter);

         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });

                //this is for testing to be fetched from API
             /*   itemList.add(new Item(1,"Bandara","Aloo, Puri,Paneer, Butter NAn","2PM-5PM","80 m","Free",R.drawable.logoeats));
        itemList.add(new Item(2,"Gurudwara","Aloo, Puri,Paneer, Butter NAn","10AM-2PM","80 m","Free",R.drawable.images));
        itemList.add(new Item(3,"Spicy Bandara","Aloo, Puri,Paneer, Butter NAn","10AM-1PM","80 m","Paid",R.drawable.logintop));
        itemList.add(new Item(1,"Bandara","Aloo, Puri,Paneer, Butter NAn","2PM-5PM","80 m","Free",R.drawable.images));
        itemList.add(new Item(2,"Gurudwara","Aloo, Puri,Paneer, Butter NAn","10AM-2PM","80 m","Free",R.drawable.images));
        itemList.add(new Item(3,"Spicy Bandara","Aloo, Puri,Paneer, Butter NAn","10AM-1PM","80 m","Paid",R.drawable.logintop));
        */

        mapFragment.getMapAsync(this);
        gpsTracker = new GPSTracker(getContext());

        if (gpsTracker.canGetLocation()) {

            if (gpsTracker == null) {
                Toast.makeText(getContext(), "gpstracker error", Toast.LENGTH_SHORT).show();
            } else {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();

            }


        } else {
            Toast.makeText(getContext(), "Can't Fetch Location", Toast.LENGTH_SHORT).show();

        }



        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //To disable Compass
        mMap.getUiSettings().setCompassEnabled(false);


        currentLatLong = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(currentLatLong).title("You are here"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLatLong)
                .zoom(17)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
