package com.example.recently;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.app.Notification.VISIBILITY_PRIVATE;
import static android.app.Notification.VISIBILITY_PUBLIC;

public class RecieverBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.OrderReciever"))
        {
            String OrderCode= intent.getStringExtra("OrderCode");
            final PendingResult result = goAsync();
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Orders").child(OrderCode).child("Accepted");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    if(((Boolean)snapshot.getValue())){
                        //ShowNotification(context,"Order Accepted","Congratulation, Your order is Accepted\n we will arrive to you in max 30 minutes");
                        Intent WaitIntent = new Intent(context,WaitForResponse.class);
                        WaitIntent.putExtra("OrderCode",OrderCode);
                        context.startForegroundService(WaitIntent);
                        result.finish();
                    }
                    else if (!((Boolean)(snapshot.getValue())))
                    {
                        ShowNotification(context,"Order Refused","Sorry, we Don't have the material for your Order and it will be Soon.ISA");
                        result.finish();
                    }
                }
                catch(Exception exp)
                {
                 exp.printStackTrace();
                }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    public void ShowNotification(Context context,String title,String Message)
    {
        PendingIntent notification = PendingIntent.getActivity(context,0,new Intent(context,MainAppActivity.class), 0);
        NotificationManager noti =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel("Order","Order","Order",noti);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"Order")
                .setContentTitle(title)
                .setContentText("About your Order")
                .setSmallIcon(R.drawable.ic_lunch_box)
                .setAutoCancel(true)
                .setNumber(1)
                .setContentIntent(notification)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(Message));
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        builder.setVibrate(new long[] {500,1000,500});
        builder.setAutoCancel(true);
        noti.cancel(1);
        noti.notify(1,builder.build());
    }
    protected void createNotificationChannel(String id, String name,String description,NotificationManager noti)
    {

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLockscreenVisibility(VISIBILITY_PUBLIC);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        noti.createNotificationChannel(channel);      //Notification Manager.
    }
}
