package edu.hacksc.trashyredditapp.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.hacksc.trashyredditapp.Event;
import edu.hacksc.trashyredditapp.MyAdapter;
import edu.hacksc.trashyredditapp.R;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileFragment extends Fragment {

    String email;
    String password;
    String first;
    String last;
    String user_id;

    ArrayList<Event> eventArrayList;

    public Profile profileRef;

    SharedPreferences sharedPreferences;

    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference();

    //private ProfileViewModel profileViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TextView display_top;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i("OnCreate", "Profile");

        Intent i = getActivity().getIntent();
        first= i.getStringExtra("first");
        last = i.getStringExtra("last");
        email = i.getStringExtra("email");
        password = i.getStringExtra("password");

        profileRef = new Profile(first, last, email, password);

        myRef.child("Users").child("Login").child("Email").setValue(email);
        myRef.child("Users").child("Login").child("First").setValue(first);
        myRef.child("Users").child("Login").child("Last").setValue(last);
        myRef.child("Users").child("Login").child("Password").setValue(password);

        //profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        //final TextView textView = root.findViewById(R.id.text_profile);
//        profileViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        user_id = sharedPreferences.getString("USER_ID", "");

        recyclerView = root.findViewById(R.id.event_recycler_view);
        display_top = root.findViewById(R.id.text_profile);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        eventArrayList = new ArrayList<Event>();

        Map<String,?> keys = sharedPreferences.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            if(entry.getKey().toString().contains("lat/lng:")) {
                Log.d("map values", entry.getKey() + ": " +
                        entry.getValue().toString());

                LatLng latLng = Event.GetLatLng(entry.getKey());

                Event event = new Event(user_id,latLng);
                eventArrayList.add(event);
            }
        }

        display_top.setText("Hello " + first + "!");

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(eventArrayList);
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
