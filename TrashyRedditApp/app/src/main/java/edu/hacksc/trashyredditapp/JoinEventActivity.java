package edu.hacksc.trashyredditapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class JoinEventActivity extends AppCompatActivity {
    private String username;
    private Event event;
    private TextView location;
    private TextView name;
    private TextView date_time;
    private TextView organizer;
    private TextView existing_members;
    public static String TAG = "I_WANNA_SLEEP";

    SharedPreferences sharedPreferences;

    public void updateUI(FirebaseUser user){
        if(user != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("USER_ID", user.getUid());
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //intent.putExtra("database", database);
            startActivity(intent);
        }
    }

    public void goToMain(View view){
        Toast.makeText(JoinEventActivity.this, "Event Joined",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinevent);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        location = findViewById(R.id.loc);
        name = findViewById(R.id.event_name);
        date_time = findViewById(R.id.date_time);
        organizer = findViewById(R.id.organizer);
        existing_members = findViewById(R.id.members);
    }


    public void register(View view){ addUser(username, event); }

    public void addUser(String username, Event event) {

//        if (event.addUser(username)) {
//            Log.i(TAG, "addtoEvent:success");
//        }
//        else {
//            Log.i(TAG, "addtoEvent:failure");
//        }
//    }
    }

}