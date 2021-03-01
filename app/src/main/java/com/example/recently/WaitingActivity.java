package com.example.recently;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.recently.databinding.ActivityWaitingBinding;

import java.nio.channels.AsynchronousChannelGroup;

public class WaitingActivity extends AppCompatActivity {
    ActivityWaitingBinding root;
    private Bundle ComeIntent;
    private String OrderCode;
    private String CurrentString;
    private int CurrentProgress=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityWaitingBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        Intent inner = getIntent();
        if(inner.hasExtra("OrderCode")) OrderCode=inner.getStringExtra("OrderCode");
        if (inner.hasExtra("CurrentProgress")) CurrentString=inner.getStringExtra("CurrentProgress");
        Log.i("Tag",OrderCode+CurrentString);
        CurrentProgress=Integer.parseInt(CurrentString);
        root.Time.setText("Remaining "+(60-CurrentProgress)+" m");
        root.progressBar5.setMax(60);
        root.progressBar5.setProgress(CurrentProgress);
        new Thread ()
        {
            @Override
            public void run() {
                super.run();
                while(CurrentProgress<60) {
                    try {
                        Thread.sleep(1000*60);
                        CurrentProgress++;
                        root.progressBar5.setProgress(CurrentProgress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

}