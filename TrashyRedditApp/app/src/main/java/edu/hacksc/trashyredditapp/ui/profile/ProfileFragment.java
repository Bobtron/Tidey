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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class ProfileFragment extends Fragment {

    String email;
    String password;

    ArrayList<Event> eventArrayList;

    SharedPreferences sharedPreferences;

    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference();

    private ProfileViewModel profileViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i("OnCreate", "Profile");

        Intent i = getActivity().getIntent();
        email = i.getStringExtra("email");
        password = i.getStringExtra("password");
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);
        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.event_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        eventArrayList = new ArrayList<Event>();

        Map<String,?> keys = SharedPreferences.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            if(entry.getKey().matches(""))
            Log.d("map values",entry.getKey() + ": " +
                    entry.getValue().toString());
        }

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);
    }


}
