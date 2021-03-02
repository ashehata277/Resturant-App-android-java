package com.example.recently;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.recently.databinding.ActivityWaitingBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.AsynchronousChannelGroup;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WaitingActivity extends AppCompatActivity {
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
    private ProgressBar progressBar;
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
        try{
            progressBar.execute();
        }catch (Exception exp)
        {
            exp.printStackTrace();
        }


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
                root.PhoneText.setText(Phone);
                root.TimeText.setText(OrderDate);
                root.PriceText.setText(Price+"  L.E");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public class ProgressBar extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            root.progressBar5.setProgress(CurrentProgress);
            root.Time.setText("Remaining "+(60-CurrentProgress)+" m");
        }
        @Override
        protected Void doInBackground(Void... voids) {
            while(CurrentProgress<60)
            {
                try {
                    Thread.sleep(1000*60);
                    CurrentProgress++;
                    publishProgress();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
    }

}