package edu.hacksc.trashyredditapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Event {
    public String user_id;
    public LatLng point;
    public ArrayList<String> participants;

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

    public boolean addUser(String user) {
        boolean found_user = false;
        for (int i=0; i<participants.size(); ++i) {
            if (participants.get(i) == user) found_user = true;
            if (found_user) { return false; }
        }
        participants.add(user);
        return true;
    }
}
