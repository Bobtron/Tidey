package edu.hacksc.trashyredditapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static LatLng getLatLng(String info){
        Pattern latPtrn = Pattern.compile("\\((-?\\d+\\.\\d+),");
        Matcher latMtchr = latPtrn.matcher(info);

        latMtchr.find();

        double lat = Double.parseDouble(latMtchr.group(1));

        Pattern lngPtrn = Pattern.compile(",(-?\\d+\\.\\d+)\\)");
        Matcher lngMtchr = lngPtrn.matcher(info);

        lngMtchr.find();

        double lng = Double.parseDouble(lngMtchr.group(1));

        return new LatLng(lat, lng);
    }
}
