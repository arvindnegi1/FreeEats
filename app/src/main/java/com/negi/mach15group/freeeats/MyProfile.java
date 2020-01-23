package com.negi.mach15group.freeeats;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MyProfile extends Fragment {

Button logout;
TextView phone,name;
    managesession ms;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_profile, container, false);
    //phone=v.findViewById(R.id.phone);

  ms=new managesession(this.getContext());
  /* try {
       phone.setText(ms.getPhone());
       name.setText(ms.getname());
   }
   catch (Exception e)
   {
       e.printStackTrace();
   }*/

        sharedPreferences=getContext().getSharedPreferences("Session",Context.MODE_PRIVATE);
      // phone.setText(sharedPreferences.getString("email",null));
    /*logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ms.logoutUser();
        }
    });*/
    return v;
    }


}
