package edu.hacksc.trashyredditapp;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event {
    public String eventName;
    public String latitude;
    public String longitude;
    public String owner;
    public int numOfGuests;
    public int upvotes;
    public int downvotes;
    public String description;
    public ArrayList<String> participants;

    public Event() {
    }
    
    public Event(String eventName, String latitude, String longitude, String owner, int numOfGuests, int upvotes,
                 int downvotes, String description) {
        this.eventName = eventName;
        this.latitude = latitude;
        this.longitude  = longitude;
        this.owner = owner;
        this.numOfGuests = numOfGuests;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.description = description;
    }


//    public boolean addUser(String user) {
//        boolean found_user = false;
//        for (int i = 0; i < participants.size(); ++i) {
//            if (participants.get(i) == user) found_user = true;
//            if (found_user) {
//                return false;
//            }
//        }
//        participants.add(user);
//        return true;
//    }

//    public static LatLng GetLatLng(String info){
//        Pattern latPtrn = Pattern.compile("\\((-?\\d+\\.\\d+),");
//        Matcher latMtchr = latPtrn.matcher(info);
//
//        latMtchr.find();
//
//        double lat = Double.parseDouble(latMtchr.group(1));
//
//        Pattern lngPtrn = Pattern.compile(",(-?\\d+\\.\\d+)\\)");
//        Matcher lngMtchr = lngPtrn.matcher(info);
//
//        lngMtchr.find();
//
//        double lng = Double.parseDouble(lngMtchr.group(1));
//
//        return new LatLng(lat, lng);
//    }
}
