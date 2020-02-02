package edu.hacksc.trashyredditapp;

import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Pin {
//    public String pinI
    public String title;
    //public LatLng location;
    public String latitude;
    public String longitude;
    public String eventID; //eventID will be null for a marker with no event
    public String userFirstName; //first name of user who created the marker

    public Pin() {
    }

    public Pin(String title, /*LatLng location*/ String latitude, String longitude, String eventID, String userFirstName) {
//        this.pinID = pinID;
        this.title = title;
        //this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.eventID = eventID;
        this.userFirstName = userFirstName;
    }
}
