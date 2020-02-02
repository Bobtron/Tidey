package edu.hacksc.trashyredditapp;

import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Pin {
    public String title;
    public LatLng location;
    public String eventID; //eventID will be null for a marker with no event
    public String userFirstName; //first name of user who created the marker

    public Pin() {
    }

    public Pin(String title, LatLng location, String eventID) {
        this.title = title;
        this.location = location;
        this.eventID = eventID;
    }
}
