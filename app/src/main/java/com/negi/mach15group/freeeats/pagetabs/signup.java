package com.negi.mach15group.freeeats.pagetabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.negi.mach15group.freeeats.AskForLocation;
import com.negi.mach15group.freeeats.Login;
import com.negi.mach15group.freeeats.managesession;
import com.negi.mach15group.freeeats.R;

import java.util.concurrent.TimeUnit;

public class signup extends AppCompatActivity implements View.OnClickListener {
    private Button button_sign,verify;
    private EditText fname,lname,email,password,mobile;
    private String mobileno,first_name,last_name,email_assdr,user_password;
    private EditText otp1,otp2,otp3,otp4,otp5,otp6;
    private LinearLayout sign,otp;
    private FirebaseAuth mauth;
    private String code,codesent;
    LocationManager manager;
    ProgressDialog Dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        button_sign=findViewById(R.id.signup_button);
        sign=findViewById(R.id.layout_sign);
        verify=findViewById(R.id.verify);
        otp=findViewById(R.id.layout_otp);
        otp1=findViewById(R.id.otp_1);
        otp2=findViewById(R.id.otp_2);
        otp3=findViewById(R.id.otp_3);
        otp4=findViewById(R.id.otp_4);
        otp5=findViewById(R.id.otp_5);
        otp6=findViewById(R.id.otp_6);
        manager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mauth=FirebaseAuth.getInstance();
        mauth.signOut();
        Dialog=new ProgressDialog(this);
        Dialog.setMessage("verifying");
        Dialog.setTitle("LOADING");
        button_sign.setOnClickListener(this);
        otp1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (otp1.getText().length() == 1)
                    otp2.requestFocus();
                return false;
            }
        });
        otp2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (otp2.getText().length() == 1)
                    otp3.requestFocus();
                return false;
            }
        });
        otp3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (otp3.getText().length() == 1)
                    otp4.requestFocus();
                return false;
            }
        });
        otp4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (otp4.getText().length() == 1)
                    otp5.requestFocus();
                return false;
            }
        });
        otp5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (otp5.getText().length() == 1)
                    otp6.requestFocus();
                return false;
            }
        });
        verify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==button_sign) {
            boolean a=checkValidity();
            if(a==true) {


                otp.setVisibility(View.VISIBLE);
                sign.setVisibility(View.GONE);
                getVerificationCode(mobile.getText().toString().trim());
                otp1.requestFocus();
            }

        }
        else if(v==verify) {
            String otp_verify=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()
                    +otp4.getText().toString()+otp5.getText().toString()+otp6.getText().toString();
                if(otp_verify.length()!=6){
                    Toast.makeText(signup.this,"INVALID OTP",Toast.LENGTH_SHORT).show();
                }
                else{
                    code=otp_verify;
                    verifycode();
                    verify.setClickable(false);
                }
        }
    }
    private void verifycode()
    {
        if (codesent != null && code != null) {
            Dialog.show();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent, code);
            signInWithPhoneAuthCredential(credential);
            }
            verify.setClickable(true);
        }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(signup.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codesent=s;
        }
    };
    private void getVerificationCode(String phoneNumber) {
        Toast.makeText(this, phoneNumber+" "+phoneNumber.trim(), Toast.LENGTH_SHORT).show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber.trim(),        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(signup.this, "Login Success", Toast.LENGTH_SHORT).show();

                            Dialog.dismiss();
                            managesession mg=new managesession(signup.this);
                            mg.createLoginSession(mobileno,"user");
                            if(ContextCompat.checkSelfPermission(signup.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED||!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                            startActivity(new Intent(signup.this,Access_location.class));
                            else
                                startActivity(new Intent(signup.this, AskForLocation.class));
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Dialog.dismiss();
                                // The verification code entered was invalid
                                verify.setClickable(true);
                                Toast.makeText(signup.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    private boolean checkValidity()
    {
        first_name=fname.getText().toString().trim();
        last_name=lname.getText().toString().trim();
        email_assdr=email.getText().toString().trim();
        user_password=password.getText().toString().trim();
        mobileno=mobile.getText().toString().trim();

        if(first_name.length()==0) {
            fname.setError("name required");
            fname.requestFocus();
            return false;
        } else if(last_name.length()==0) {
            lname.setError("last name required");
            lname.requestFocus();
            return false;
        }else if (mobileno.length() == 0) {
            mobile.setError("Mobile Number Required");
            mobile.requestFocus();
            return false;
        } else if (mobileno.length() < 10) {
            mobile.setError("Mobile Number Invalid");
            mobile.requestFocus();
            return false;
        }else if(user_password.length()==0){
            password.setError("Password Required");
            password.requestFocus();
            return false;
        }
    return true;
    }
}
