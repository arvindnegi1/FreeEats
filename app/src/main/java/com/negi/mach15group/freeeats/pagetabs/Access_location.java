package com.negi.mach15group.freeeats.pagetabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.negi.mach15group.freeeats.AskForLocation;
import com.negi.mach15group.freeeats.GPSTracker;
import com.negi.mach15group.freeeats.R;

public class Access_location extends AppCompatActivity {
Button turnon;
LocationManager manager;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_location);
        turnon=findViewById(R.id.turn_on_gps);
         manager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gps=new GPSTracker(Access_location.this);
        if(!gps.canGetLocation()) {
            turnon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    gps.showSettingsAlert(35,Access_location.this);
                }
            });
        }
        else
            startActivity(new Intent(Access_location.this,AskForLocation.class));




    }
  /*  public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(Access_location.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
               if(ActivityCompat.shouldShowRequestPermissionRationale(Access_location.this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(Access_location.this);
                alertDialog.setTitle("Permission")
                        .setMessage("Location access needed")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              ActivityCompat.requestPermissions(Access_location.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},90);
                            }
                        })
                        .create()
                        .show();

            }
            else
            {
                ActivityCompat.requestPermissions(Access_location.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},90);
            }


        }


            return true;
    }*/
}
