package com.negi.mach15group.freeeats;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddEvent extends Fragment {
private EditText startTimeET, endTimeET;
private Button itemadd;
ListView ls;
EditText et;
   String list;
    ArrayAdapter<String>adapter;
    SharedPreferences pref;
    String mobile;
    Button request_upload;
FirebaseDatabase firebaseDatabase;
DatabaseReference mref;
EditText event;
GPSTracker gps;
   public String event_name,item_list,start_stop_time,type,lat,lon;
    public int images;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_add_event, container, false);
        list="";
        startTimeET = view.findViewById(R.id.start_time_add_food);
        itemadd=view.findViewById(R.id.add_items_button);
        event=view.findViewById(R.id.enter_event_name);
        endTimeET = view.findViewById(R.id.end_time_add_food);
        request_upload=view.findViewById(R.id.request_upload_button);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mref=firebaseDatabase.getReference("User");
        ls=view.findViewById(R.id.list_items);
        et=view.findViewById(R.id.add_items);
        pref=getContext().getSharedPreferences("Session",Context.MODE_PRIVATE);
        mobile=pref.getString("email","NULL");
    Toast.makeText(getContext(),""+mobile,Toast.LENGTH_SHORT).show();

        //Toast.makeText(getContext(),"hello",Toast.LENGTH_SHORT).show();

    gps=new GPSTracker(getContext());


      ls.setAdapter(adapter);
        startTimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                TimePickerDialog picker = new TimePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                //eText.setText(sHour + ":" + sMinute);
                                startTimeET.setText(sHour + ":" + sMinute);
                            }
                        }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false);
                picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                picker .show();
            }
        });

        endTimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                TimePickerDialog picker = new TimePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                //eText.setText(sHour + ":" + sMinute);
                                endTimeET.setText(sHour + ":" + sMinute);
                            }
                        }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false);
                picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                picker .show();
            }
        });
        itemadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list=list+et.getText().toString()+",";





            }
        });
        request_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Item(String event_name, String item_list, String start_stop_time,  String type, int images, String lat, String lon)
                Item item=new Item(event.getText().toString(),list,startTimeET.getText().toString(),endTimeET.getText().toString(),"FREE",R.drawable.logoeats,gps.getLatitude(),gps.getLongitude(),mobile);
                final String curdate;
                 curdate=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                mref.child(curdate).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(),"succesfull",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;

    }


}
