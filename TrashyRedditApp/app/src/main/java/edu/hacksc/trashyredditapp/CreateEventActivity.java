package edu.hacksc.trashyredditapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class CreateEventActivity extends AppCompatActivity {
    private String loc;
    private TextView location;
    private EditText eventNameText;
    private Button createEventButton;
    public static String TAG = "I_WANNA_SLEEP";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        Intent i = getIntent();
        String loc = i.getStringExtra("location");

        location = findViewById(R.id.loc);
        location.setText("Location: \n" + loc);
        eventNameText = findViewById(R.id.editTextEventName);
        createEventButton = findViewById(R.id.create);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                String eventName = eventNameText.getText().toString();
                i.putExtra("eventName", eventName);
                startActivity(i);
                Toast.makeText(CreateEventActivity.this, "New Event Saved",
                        Toast.LENGTH_SHORT).show();
            }
        });

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}