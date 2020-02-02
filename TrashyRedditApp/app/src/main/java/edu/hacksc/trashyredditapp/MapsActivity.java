package edu.hacksc.trashyredditapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import android.widget.Button;
import android.widget.Toast;

//import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.location;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , LocationListener {
    //private MapInfoWindowFragment mapInfoWindowFragment;
    private GoogleMap mMap;
    private Marker mMarker;
    String user_id;
    Button pickConfirm;
    Button pickReject;
    private LocationManager locationManager;

    private DatabaseReference mDatabase;

    SharedPreferences sharedPreferences;

        private static final long MIN_TIME = 400;
        private static final float MIN_DISTANCE = 1000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            Log.i("hi", "onCreateMapFragment");

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            mDatabase = FirebaseDatabase.getInstance().getReference();

            user_id = sharedPreferences.getString("USER_ID", "");

            //BottomNavigationView bnv = findViewById(R.id.bnv);
            //Log.i("hi", bnv.toString());

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (lastKnownLocation != null) {
//                updateMap(lastKnownLocation);
//                Log.i(MainActivity.APP_TAG, "GOING TO LAST KNOWN LOCATION");
//            }else{
//                Log.i(MainActivity.APP_TAG, "THE LAST KNOWN LOCATION IS NULL");
//            }
            }

            //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            BottomNavigationView bnv = findViewById(R.id.nav_view);
            bnv.bringToFront();

            bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Log.i("hi", "gotomain");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                }
            });
        }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override

    public void onMapReady(GoogleMap googleMap) {
        Log.i("hi", "onMapReady");

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.animateCamera( CameraUpdateFactory.zoomTo( 2.0f ));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        Map<String,?> keys = sharedPreferences.getAll();
//        for(Map.Entry<String,?> entry : keys.entrySet()){
//            if(entry.getKey().toString().contains("lat/lng:")) {
//                Log.d("map values", entry.getKey() + ": " +
//                        entry.getValue().toString());
//
//                LatLng latLng = Event.GetLatLng(entry.getKey());
//                mMap.addMarker(new MarkerOptions().position(latLng));
//
//            }
//        }
        mDatabase.child("pins").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    Pin pin = data.getValue(Pin.class);
                    Log.d(LoginActivity.TAG, "here " + pin.latitude);

                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(pin.latitude), Double.parseDouble(pin.longitude))).title("Is this a trash site?"));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(LoginActivity.TAG, "Failed to read value.", error.toException());
            }
        });

//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//           @Override
//           public boolean onMarkerClick(Marker marker){
//               SharedPreferences.Editor editor = sharedPreferences.edit();
//               if (true){
//                   Intent i = new Intent(getApplicationContext(), CreateEventActivity.class);
//                   String pos = marker.getPosition().toString();
//                   i.putExtra("location", pos);
//                   //startActivity(i);
//
//                   return true;
//               }
//
//               return false;
//           }
//
//       });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(com.google.android.gms.maps.model.Marker marker) {
                if (marker.getTitle().equals("Click here if you would like to create a clean up event")) {
                    Intent i = new Intent(getApplicationContext(), CreateEventActivity.class);
                    String pos = marker.getPosition().toString();
                    String stringID = marker.getId();
                    i.putExtra("location", pos);
                    //i.putExtra("pinID", pinID);
                    startActivity(i);
                }
            }
        });


       mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
           @Override
           public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
               marker.setTitle("Click here if you would like to create a clean up event");
               Log.v("MARKER", marker.getTitle() + " is the marker title");
               marker.showInfoWindow();
               return false;
           }
       });

        mMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
//                mMarker.setPosition(point);
                if (mMarker != null) {
                    //User has clicked a part of the map when prompted yes or no buttons to the question
                    mMarker.remove();
                    mMarker = null;
                    pickConfirm.setVisibility(View.INVISIBLE);
                    pickReject.setVisibility(View.INVISIBLE);
                }
                else {
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                    editor.putString(point.toString(), user_id);
//                    editor.commit();

                    Log.i(LoginActivity.TAG, sharedPreferences.getString("  USER_ID", "") + " " + point.toString());
                    Log.i("MARKER", "Created Marker");
                    mMarker = mMap.addMarker(new MarkerOptions().position(point).title("Is this a trash site?"));
                    mMarker.showInfoWindow();
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(point));

                    pickConfirm = findViewById(R.id.confirmTrashSite);
                    pickReject = findViewById(R.id.rejectTrashSite);
                    pickConfirm.setVisibility(View.VISIBLE);
                    pickReject.setVisibility(View.VISIBLE);
                    pickConfirm.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Log.d("MARKER", "in the yes click listener");
                            pickConfirm.setVisibility(View.INVISIBLE);
                            pickReject.setVisibility(View.INVISIBLE);
                            mMarker.setTitle("Click here if you would like to create a clean up event");
                            mMarker.hideInfoWindow();
                            //String pinID = mDatabase.push().getKey();
                            Log.d("MARKER", mMarker.getTitle() + "is the title");
                            Log.d("MARKER", mMarker.getPosition() + "is the location");
//                            Pin pin = new Pin(mMarker.getTitle(), mMarker.getPosition(), null, user_id);
//                            mDatabase.child("pins").child("test").setValue(pin);
                            Toast.makeText(getApplicationContext(), "Successfully created a trash site", Toast.LENGTH_SHORT).show();


                            String markerID = mDatabase.push().getKey();

                            Pin pin = new Pin(mMarker.getTitle(), "" + mMarker.getPosition().latitude, "" + mMarker.getPosition().longitude, "" + -1, sharedPreferences.getString("first", ""), sharedPreferences.getString("USER_ID", ""));
//                            Pin pin = new Pin(mMarker.getTitle(), mMarker.getPosition(), null);
                            mDatabase.child("pins").child(markerID).setValue(pin);
                            mMarker.remove();
                            mMarker = null;


                        }
                    });
                    pickReject.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            pickConfirm.setVisibility(View.INVISIBLE);
                            pickReject.setVisibility(View.INVISIBLE);
                            mMarker.remove();
                            mMarker = null;
                            Toast.makeText(getApplicationContext(), "Did not create a trash site", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //                mMarker.setPosition(point);
            }
        });

//        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//            @Override
//            //Default InfoWindow frame
//            public View getInfoWindow(Marker marker) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                final View infoview = getLayoutInflater().inflate(R.layout.activity_maps, null);
//                LatLng latLng = marker.getPosition();
//
//                pickConfirm = infoview.findViewById(R.id.confirmTrashSite);
//                pickReject = infoview.findViewById(R.id.rejectTrashSite);
//                pickConfirm.setOnClickListener(new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        Toast.makeText(getApplicationContext(), "Successfully created a trash site", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                pickReject.setOnClickListener(new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        mMarker.remove();
//                        Toast.makeText(getApplicationContext(), "Did not create a trash site", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//                return infoview;
//            }
//        });


    }
    @Override
    public void onLocationChanged (Location location){
        if (mMap != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
            mMap.animateCamera(cameraUpdate);
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged (String provider,int status, Bundle extras){
    }

    @Override
    public void onProviderEnabled (String provider){
    }

    @Override
    public void onProviderDisabled (String provider){
    }

//    @Override
//    public boolean onMarkerClick( final Marker marker){
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Log.d("hello", "line reached");
////        String result = sharedPreferences.getString(marker.getPosition().toString(), "");
////        if (result.length() > 0 && result.equals(user_id)) {
////            marker.remove();
////            editor.remove(marker.getPosition().toString());
//            Intent i = new Intent(getApplicationContext(), CreateEventActivity.class);
//            String pos = marker.getPosition().toString();
//            i.putExtra("location", "pos");
//
//
//            startActivity(i);
//            return true;
////        }
//
////        return false;
//    }


}

