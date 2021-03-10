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
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static android.app.Notification.DEFAULT_ALL;
import static android.app.Notification.DEFAULT_VIBRATE;
import static android.app.Notification.VISIBILITY_PUBLIC;

public class WaitForResponse extends Service {
    private int CurrentProgress=0;
    private NotificationCompat.Builder builder;
    private NotificationManager noti;
    private UpdateBar updateBar=new UpdateBar();
    private String OrderCode;
    private Intent content;
    private PendingIntent notification;
    private ForBound forBound = new ForBound();
    private int i;
    public WaitForResponse()
    {

    }

    public void StopSelf(int ServiceId)
    {
        WaitForResponse.this.stopSelf(ServiceId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        i=startId;
        OrderCode = intent.getStringExtra("OrderCode");
        startForeground(startId,ShowNotification(startId,"Order Accepted","Congratulation, Your order is Accepted\n we will arrive to you in max 1 hour"));
       try{
           updateBar.execute();
       }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return forBound;
    }
    public Notification ShowNotification(int startId,String title,String Message)
    {
        content =new Intent(WaitForResponse.this,WaitingActivity.class);
        content.putExtra("CurrentProgress",String.valueOf(CurrentProgress));
        content.putExtra("OrderCode",OrderCode);
        content.putExtra("ServiceId",startId);
        notification= PendingIntent.getActivity(WaitForResponse.this,0,content, PendingIntent.FLAG_UPDATE_CURRENT);
        noti =(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel("Order","Order","Order",noti);
        builder = new NotificationCompat.Builder(WaitForResponse.this,"Order")
                .setContentTitle(title)
                .setContentText("About your Order")
                .setSmallIcon(R.drawable.ic_lunch_box)
                .setAutoCancel(true)
                .setNumber(1).setDefaults(DEFAULT_ALL)
                .setContentIntent(notification)
                .setProgress(60,CurrentProgress,false)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(Message));
        builder.setNotificationSilent();
        builder.setAutoCancel(false);
        noti.cancel(1);
        noti.notify(1,builder.build());
        return builder.build();
    }
    protected void createNotificationChannel(String id, String name,String description,NotificationManager noti)
    {

        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLockscreenVisibility(VISIBILITY_PUBLIC);
        channel.setLightColor(Color.RED);
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
            noti.notify(i,builder.build());
            WaitForResponse.this.stopSelf();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            content =new Intent(WaitForResponse.this,WaitingActivity.class);
            content.putExtra("CurrentProgress",String.valueOf(CurrentProgress));
            content.putExtra("OrderCode",OrderCode);
            notification= PendingIntent.getActivity(WaitForResponse.this,0,content, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setProgress(60,CurrentProgress,false);
            noti.notify(i,builder.build());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while(CurrentProgress<60) {
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
   public class  ForBound extends Binder
    {
        public WaitForResponse getServiceInstance()
        {
            return WaitForResponse.this;
        }
    }
}