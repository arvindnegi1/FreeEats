package com.negi.mach15group.freeeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.negi.mach15group.freeeats.pagetabs.signup;

public class Login extends AppCompatActivity implements View.OnClickListener {
TextView tv;
Button sign;
String mobileno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv=findViewById(R.id.login_username_et);
        sign=findViewById(R.id.login_button);
        sign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==sign){
            if(checkValidity()){
               Intent intent=new Intent(Login.this,signup.class);
               intent.putExtra("phone",mobileno);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
            }
        }
    }

    private boolean checkValidity()
    {

        mobileno=tv.getText().toString().trim();

         if (mobileno.length() == 0) {
            tv.setError("Mobile Number Required");
            tv.requestFocus();
            return false;
        } else if (mobileno.length() < 10) {
            tv.setError("Mobile Number Invalid");
            tv.requestFocus();
            return false;
        }
        return true;
    }

}
