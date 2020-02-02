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
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    String email;
    String password;
    FirebaseUser user;
    EditText first_name;
    EditText last_name;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        email = intent.getExtras().getString("email");
        password = intent.getExtras().getString("password");
        user = intent.getExtras().getParcelable("user_parcel");

        first_name = findViewById(R.id.first_name_text);
        last_name = findViewById(R.id.last_name_text);
    }

    public void onRegister(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String first_name_str = first_name.getText().toString().trim();
        String last_name_str = last_name.getText().toString().trim();

        editor.putString("USER_ID", user.getUid());
        //TODO put whatever else you want
        editor.commit();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("first", first_name_str);
        intent.putExtra("last", last_name_str);
        //intent.putExtra("database", database);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

}
