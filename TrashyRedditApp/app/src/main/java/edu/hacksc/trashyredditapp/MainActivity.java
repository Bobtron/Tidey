package edu.hacksc.trashyredditapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import edu.hacksc.trashyredditapp.ui.profile.ProfileFragment;
import edu.hacksc.trashyredditapp.ui.profile.ProfileViewModel;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    public String first;
    public String last;
    public String email;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

//        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//                = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment fragment;
//                Log.i("ARGH", item.toString());
//                return false;
//            }
//        };
//
//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_map, R.id.navigation_bookmarks, R.id.navigation_search, R.id.navigation_notifications, R.id.navigation_profile    )
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        Intent i = getIntent();
        first = i.getStringExtra("first");
        last = i.getStringExtra("last");
        email = i.getStringExtra("email");
        password = i.getStringExtra("password");

        Intent intent = new Intent(getApplicationContext(), ProfileViewModel.class);

        SharedPreferences.Editor editor = sharedPreferences.edit();
    }

}
