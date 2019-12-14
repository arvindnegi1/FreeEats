package com.negi.mach15group.freeeats;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class My_event extends Fragment {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mref;
    List<Item>list;
    String mobile;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_event, container, false);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mref=firebaseDatabase.getReference("User");
        recyclerView=view.findViewById(R.id.recycle);
        list=new ArrayList<>();
        sharedPreferences=getContext().getSharedPreferences("Session",Context.MODE_PRIVATE);
            mobile=sharedPreferences.getString("email",null);
        recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                       if(ds.child("phone").getValue().toString().equals(mobile)) {
                           Item temp = new Item(ds.child("event_name").getValue().toString(),
                                   ds.child("item").getValue().toString(),
                                   ds.child("start_time").getValue().toString(),
                                   ds.child("stop_time").getValue().toString(),
                                   "free",
                                   R.drawable.logoeats,
                                   Double.parseDouble(String.valueOf(ds.child("lat").getValue())),
                                   Double.parseDouble(String.valueOf(ds.child("lon").getValue()))
                                   , ds.child("phone").getValue().toString());
                           list.add(temp);
                       }
                    }
                    ProductAdapter adapter=new ProductAdapter(getContext(),list);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        return  view;
    }


}
