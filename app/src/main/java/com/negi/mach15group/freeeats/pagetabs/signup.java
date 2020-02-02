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
    private EditText fname,lname;
    private String mobileno,first_name,last_name;
    private EditText otp_new;
    private LinearLayout sign,otp;
    private FirebaseAuth mauth;
    private String code,codesent;
    LocationManager manager;
    ProgressDialog Dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
       Intent intent=getIntent();
       mobileno=intent.getStringExtra("phone");
        getVerificationCode(mobileno);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        button_sign=findViewById(R.id.signup_button);
        sign=findViewById(R.id.layout_sign);
        verify=findViewById(R.id.verify);
        otp_new=findViewById(R.id.otp_new);
        otp=findViewById(R.id.layout_otp);
        manager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mauth=FirebaseAuth.getInstance();
        mauth.signOut();
        Dialog=new ProgressDialog(this);
        Dialog.setMessage("verifying");
        Dialog.setTitle("LOADING");

        verify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==verify) {
            String otp_verify=otp_new.getText().toString();
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
                            final managesession mg=new managesession(signup.this);

                            otp.setVisibility(View.GONE);
                            sign.setVisibility(View.VISIBLE);
                            button_sign.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkValidity()) {

                                        mg.createLoginSession(mobileno,first_name+last_name);
                                        startActivity(new Intent(signup.this, Access_location.class));
                                    }
                                }
                            });

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



        if(first_name.length()==0) {
            fname.setError("name required");
            fname.requestFocus();
            return false;
        } else if(last_name.length()==0) {
            lname.setError("last name required");
            lname.requestFocus();
            return false;
        }
    return true;
    }
}
