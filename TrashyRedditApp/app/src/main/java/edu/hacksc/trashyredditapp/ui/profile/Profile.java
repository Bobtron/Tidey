package edu.hacksc.trashyredditapp.ui.profile;

import android.content.Intent;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Profile {

    public String email;
    public String password;

    public String first;
    public String last;

    public Profile(){

    }

    public Profile(String first, String last, String email, String password) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.password = password;
    }
}
