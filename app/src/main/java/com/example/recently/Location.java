package com.example.recently;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

public class Location extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button Confirm;
    private LatLng lng;
    private static LatLng finalLocation;         //we make it static to can share it with the order class.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Confirm = (Button) findViewById(R.id.confirm);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalLocation = lng;
                startActivity(new Intent(Location.this, Order.class));
                finish();
            }
        });
    }
    public static LatLng getLocation() {
        return finalLocation;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        //android.location.Location myLocation = mMap.getMyLocation();
        LatLng sydney = new LatLng( 27.253630, 33.813305 ) ;
        lng=sydney;            //to make default location //this can resolve Bug.
        mMap.addMarker(new MarkerOptions().
                position(sydney).title("Home"))
                .showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));
        // Add a marker in Sydney and move the camera
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("myLocation")).showInfoWindow();
                lng = latLng;
            }
        });
    }
}