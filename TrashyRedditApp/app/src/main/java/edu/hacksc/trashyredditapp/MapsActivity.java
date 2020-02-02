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

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener, LocationListener {
     //private MapInfoWindowFragment mapInfoWindowFragment;
        private GoogleMap mMap;
        private Marker mMarker;
        String user_id;
        Button pickConfirm;
        Button pickReject;
        private LocationManager locationManager;

        SharedPreferences sharedPreferences;

        private static final long MIN_TIME = 400;
        private static final float MIN_DISTANCE = 1000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            Log.i("hi", "onCreateMapFragment");

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

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

        Map<String,?> keys = sharedPreferences.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            if(entry.getKey().toString().contains("lat/lng:")) {
                Log.d("map values", entry.getKey() + ": " +
                        entry.getValue().toString());

                LatLng latLng = Event.getLatLng(entry.getKey());
                mMap.addMarker(new MarkerOptions().position(latLng));

            }
        }


        mMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
//                mMarker.setPosition(point);
                if (mMarker != null && mMarker.getTitle().equals("Is this a trash site?")) {
                    //User has clicked a part of the map when prompted yes or no buttons to the question
                    mMarker.remove();
                    mMarker = null;
                    pickConfirm.setVisibility(View.INVISIBLE);
                    pickReject.setVisibility(View.INVISIBLE);
                }
                else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString(point.toString(), user_id);
                    editor.commit();

                    Log.i(LoginActivity.TAG, sharedPreferences.getString("USER_ID", "") + " " + point.toString());
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
                            pickConfirm.setVisibility(View.INVISIBLE);
                            pickReject.setVisibility(View.INVISIBLE);
                            mMarker.setTitle("Trash Site");
                            Toast.makeText(getApplicationContext(), "Successfully created a trash site", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onMarkerClick( final Marker marker){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String result = sharedPreferences.getString(marker.getPosition().toString(), "");
        if (result.length() > 0 && result.equals(user_id)) {
            marker.remove();
            editor.remove(marker.getPosition().toString());
            return true;
        }
        return false;
    }
}

