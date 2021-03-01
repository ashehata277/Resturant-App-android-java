package com.example.recently;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static android.app.Notification.VISIBILITY_PUBLIC;

public class WaitForResponse extends Service {
    private int CurrentProgress=0;
    private NotificationCompat.Builder builder;
    private NotificationManager noti;
    private UpdateBar updateBar=new UpdateBar();
    public WaitForResponse() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        startForeground(1,ShowNotification("Order Accepted","Congratulation, Your order is Accepted\n we will arrive to you in max 1 hour"));
        updateBar.execute();
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public Notification ShowNotification(String title,String Message)
    {
        PendingIntent notification = PendingIntent.getActivity(WaitForResponse.this,0,new Intent(WaitForResponse.this,MainAppActivity.class), 0);
        noti =(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel("Order","Order","Order",noti);
         builder = new NotificationCompat.Builder(WaitForResponse.this,"Order")
                .setContentTitle(title)
                .setContentText("About your Order")
                .setSmallIcon(R.drawable.ic_lunch_box)
                .setAutoCancel(true)
                .setNumber(1)
                .setContentIntent(notification)
                .setProgress(100,CurrentProgress,false)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(Message));
        builder.setAutoCancel(false);
        noti.cancel(1);
        noti.notify(1,builder.build());
        return builder.build();
    }
    protected void createNotificationChannel(String id, String name,String description,NotificationManager noti)
    {

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLockscreenVisibility(VISIBILITY_PUBLIC);
        channel.setLightColor(Color.RED);
        channel.enableVibration(false);
        noti.createNotificationChannel(channel);      //Notification Manager.
    }
    public  class UpdateBar extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);
            builder.setProgress(0,0,false);
            noti.notify(1,builder.build());
            WaitForResponse.this.stopSelf();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            builder.setProgress(100,CurrentProgress,false);
            noti.notify(1,builder.build());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while(CurrentProgress<100) {
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