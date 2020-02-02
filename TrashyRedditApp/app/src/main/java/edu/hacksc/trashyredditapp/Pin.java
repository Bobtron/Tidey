package edu.hacksc.trashyredditapp;

import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Pin {
    public String title;
    public LatLng location;
    public String eventID; //eventID will be null for a marker with no event
    public String userID; //should never be null

    public Pin() {
    }

    public Pin(String title, LatLng location, String eventID, String userID) {
        this.title = title;
        this.location = location;
        this.eventID = eventID;
        this.userID = userID;
    }
}
