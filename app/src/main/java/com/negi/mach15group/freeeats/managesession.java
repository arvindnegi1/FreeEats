package com.negi.mach15group.freeeats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.negi.mach15group.freeeats.pagetabs.Access_location;

public class managesession {
 private Context context;
 private SharedPreferences pref;
 private SharedPreferences.Editor editor;

 //shared preference mode
    int PRIVATE_MODE=0;
    //preference file name
    private static final String PREF_NAME="Session";
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_MOBILE = "email";

    public static final String KEY_NAME = "name";
    //constructor
    public managesession(Context context)
    {
        this.context=context;
        pref=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=pref.edit();
    }

    //For Creating Session
    public void createLoginSession(String mobile,String name){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_MOBILE, mobile);

        editor.putString(KEY_NAME, name);
        editor.commit();
    }
    //check login
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, Login.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

        else
            context.startActivity(new Intent(context, Access_location.class));

}
    //For Clearing sharedPreference
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
