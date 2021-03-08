package com.example.recently;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.recently.databinding.ActivitySplashBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {
    private ActivitySplashBinding root;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private DatabaseReference rootref;
    private String phoneString;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        root = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash);
        CheckInternetConnection testNetwork =  new CheckInternetConnection(Splash.this);
        if(!testNetwork.isConnecting())
        {
            Toast.makeText(Splash.this,"Sorry, this app new internet connection, please check your network and try again",Toast.LENGTH_LONG).show();
        }
        root.logo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                init();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void init ()
    {
        preferences = getSharedPreferences("UserData",Context.MODE_PRIVATE);
        editor = preferences.edit();
        rootref = FirebaseDatabase.getInstance().getReference();
        if(!preferences.getString("username","N/A").equals("N/A") && !preferences.getString("password","N/A").equals("N/A") && !preferences.getString("password","N/A").isEmpty() && !preferences.getString("username","N/A").isEmpty())
        {
            phoneString = preferences.getString("phoneString","N/A");
            password =preferences.getString("password","N/A");
            Login();
        }
        else
        {
            startActivity(new Intent(Splash.this,MainActivity.class));
            finish();
        }

    }
    private void Login()
    {
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(phoneString).getKey().equals(phoneString))
                {
                    if(snapshot.child("Users").child(phoneString).child("password").getValue().toString().equals(password))
                    {
                            MainActivity.fragmentphone=phoneString;
                            startActivity(new Intent(Splash.this,MainAppActivity.class));
                            finish();
                    }
                    else
                    {
                        startActivity(new Intent(Splash.this,MainActivity.class));
                        finish();
                    }

                }
                else
                {
                    startActivity(new Intent(Splash.this,MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}