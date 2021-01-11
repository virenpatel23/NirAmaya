package com.example.niramaya;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getNotification().getBody());
    }

   public void showNotification(String message){
       PendingIntent pi = PendingIntent.getActivity(this,0,new Intent(this , DashBoard.class),0);

       Notification notification = new NotificationCompat.Builder(this)
               .setContentTitle("Alert")
               .setContentText(message)
                .setContentIntent(pi)
               .setPriority(NotificationCompat.PRIORITY_DEFAULT)
               .setAutoCancel(true)
               .build();

       NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       notificationManager.notify(0,notification);
   }




}
