package com.negi.mach15group.freeeats.pagetabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.negi.mach15group.freeeats.R;
import com.negi.mach15group.freeeats.managesession;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                managesession sessionManager = new managesession(splashscreen.this);
                sessionManager.checkLogin();
                finish();
            }
        }, 3000);
    }

}
