package com.negi.mach15group.freeeats;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AskForLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private double latitude, longitude;
    private String address;
    private EditText currentLocationET;
    private LatLng currentLatLong;
    private Button setLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gpsTracker = new GPSTracker(this);
        currentLocationET = findViewById(R.id.current_location_et);
        setLocationButton = findViewById(R.id.set_location_button);

        if (gpsTracker.canGetLocation()) {

            if (gpsTracker == null) {
                Toast.makeText(this, "gpstracker error", Toast.LENGTH_SHORT).show();
            } else {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                setDefaultAddrss(latitude, longitude);
            }
            currentLocationET.setText(address);

        } else {
            Toast.makeText(this, "Can't Fetch Location", Toast.LENGTH_SHORT).show();
            return;
        }

        setLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentAddress = currentLocationET.getText().toString();
                if(currentAddress.length() > 0){
                    startActivity(new Intent(AskForLocation.this, Dashboard.class));
                }
                else
                    Toast.makeText(AskForLocation.this, "Didn't get the address", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDefaultAddrss(double latitude, double longitude) {
        Geocoder geocoder;

        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());
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

    public void init(){
        gpsTracker = new GPSTracker(this);
        currentLocationET = findViewById(R.id.current_location_et);
        setLocationButton = findViewById(R.id.set_location_button);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //To disable Compass
        mMap.getUiSettings().setCompassEnabled(false);

        // Add a marker in Sydney and move the camera
        currentLatLong = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(currentLatLong).title("You are here"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLatLong)
                .zoom(17)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}


