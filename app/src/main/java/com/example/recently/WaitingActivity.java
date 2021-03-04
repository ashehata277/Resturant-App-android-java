package com.example.recently;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.recently.databinding.ActivityWaitingBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.AsynchronousChannelGroup;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WaitingActivity extends AppCompatActivity implements ServiceConnection {
    ActivityWaitingBinding root;
    private Bundle ComeIntent;
    private String OrderCode;
    private String CurrentString;
    private int CurrentProgress=0;
    private DatabaseReference reference;
    private SimpleDateFormat format;
    private String DatetoDay;
    private String Phone;
    private String OrderDate;
    private Long Price;
    private WaitForResponse.ForBound forBound;
    private WaitForResponse waitForResponse;
    private double latitude=0.00;
    private double longitude=0.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityWaitingBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        DatetoDay = format.format(Calendar.getInstance().getTimeInMillis());
        reference = FirebaseDatabase.getInstance().getReference();
        Intent inner = getIntent();
        if(inner.hasExtra("OrderCode")) OrderCode=inner.getStringExtra("OrderCode");
        if (inner.hasExtra("CurrentProgress")) CurrentString=inner.getStringExtra("CurrentProgress");
        Log.i("Tag",OrderCode+"      "+CurrentString);
        CurrentProgress=Integer.parseInt(CurrentString);
        root.Time.setText("Remaining "+(60-CurrentProgress)+" m");
        root.progressBar5.setMax(60);
        root.progressBar5.setProgress(CurrentProgress);
        getDataFromCloud();
        Intent stopIntent =new Intent (WaitingActivity.this,WaitForResponse.class);
        bindService(stopIntent,this,0);
        ChangeUI ui = new ChangeUI();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while(CurrentProgress<60)
                {
                try
                    {
                        Thread.sleep(1000*60);
                        CurrentProgress++;
                        Message msg = new Message();
                        msg.arg1=CurrentProgress;
                        ui.sendMessage(msg);
                    }
                catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
            }
            }
        }.start();
        root.Delivered.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try{
                    waitForResponse.StopSelf();
                    root.Bar.setVisibility(View.INVISIBLE);
                    root.Final.setVisibility(View.VISIBLE);
                    root.FinalMessage.setText("We Wish you a delicious meal\n if you want send us your feedback at the rates");
                }catch (Exception exp)
                {
                    exp.printStackTrace();
                }

            }
        });
        root.Done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getDataFromCloud()
    {
        reference = reference.child("Accepted Orders").child(DatetoDay).child(OrderCode);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrderDate=String.valueOf(snapshot.child("OrderDate").getValue());
                Phone = String.valueOf(snapshot.child("phone").getValue());
                Price = (Long) snapshot.child("Total Price").getValue();
                latitude=(double)snapshot.child("Location").child("latitude").getValue();
                longitude=(double)snapshot.child("Location").child("longitude").getValue();
                root.PhoneText.setText(Phone);
                root.TimeText.setText(OrderDate);
                root.PriceText.setText(Price+"  L.E");
                root.location.setText(latitude+":"+longitude+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public class ChangeUI extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            root.Time.setText("Remaining "+(60-msg.arg1)+" m");
            root.progressBar5.setProgress(msg.arg1);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
            forBound = (WaitForResponse.ForBound) service;
            waitForResponse = forBound.getServiceInstance();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}