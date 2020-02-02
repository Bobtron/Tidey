package edu.hacksc.trashyredditapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.hacksc.trashyredditapp.ui.profile.ProfileFragment;

import android.os.Parcel;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email_text;
    private EditText password_text;
    public static String TAG = "I_WANNA_SLEEP";

    SharedPreferences sharedPreferences;

    //public FirebaseDatabase database = FirebaseDatabase.getInstance();
    //public DatabaseReference myRef = database.getReference();

    //myRef.setValue("Hello, World!");

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        email_text = findViewById(R.id.email_text);
        password_text = findViewById(R.id.password_text);
    }

    public void register(View view){
        createUser(email_text.getText().toString().trim(), password_text.getText().toString().trim());
    }

    public void onLogin(View view){
        loginUser(email_text.getText().toString().trim(), password_text.getText().toString().trim());
    }

    public void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                            intent.putExtra("email", email_text.toString());
                            intent.putExtra("password", password_text.toString());
                            intent.putExtra("user_parcel", user);
                            startActivity(intent);

                            //updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Account Creation failed: " +task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                        //Map<String, Object> behavior = new HashMap<>();
                        //behavior.put("Likes: ", "");
                        //behavior.put("Current Events: ", "");

                        //database.collection("Users").document(email).set(behavior);
                        //DatabaseReference myRef = database.getReference("Users");
                        //myRef.setValue("hello world!");
                    }
                });
    }

    public void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...

                    }
                });
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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