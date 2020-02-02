package edu.hacksc.trashyredditapp;

import com.google.android.gms.maps.model.LatLng;

public class Event {
    public String user_id;
    public LatLng point;

    public Event(String user_id, LatLng point) {
        this.user_id = user_id;
        this.point = point;
    }

    public String getUser_id() {
        return user_id;
    }

    public LatLng getPoint() {
        return point;
    }
}
