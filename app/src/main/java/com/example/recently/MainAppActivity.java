package com.example.recently;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.recently.databinding.ActivityMainAppBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainAppActivity extends AppCompatActivity
{
    private ActivityMainAppBinding root;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        root = ActivityMainAppBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        root.nav.setOnNavigationItemSelectedListener(navListener);
        root.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainAppActivity.this,Card.class));
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.logout :
                {

                    Intent logout = new Intent (MainAppActivity.this,MainActivity.class);
                    logout.putExtra("MainApp",false);
                     /* Map<String,Object> active = new HashMap<String,Object>();
                    active.put("activeNow",false);
                    rootref.child("Users").child(phoneString).updateChildren(active);*/
                    startActivity(logout);
                    break;
                }
                case R.id.menu:
                {
                    Fragment newFragment = new MenuFragment();
                    changeFragment(newFragment);
                    break;
                }
                case R.id.offer:
                {
                    Fragment newFragment = new offerFragment();
                    changeFragment(newFragment);
                    break;
                }
                case R.id.profile:
                {
                    Fragment newFragment = new profileFragment();
                    changeFragment(newFragment);
                    break;
                }
            }
            return true;
        }
    };
    public void changeFragment(Fragment frg)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Containerfragment, frg);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}