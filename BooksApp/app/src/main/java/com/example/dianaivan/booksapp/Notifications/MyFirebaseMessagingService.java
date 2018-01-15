package com.example.dianaivan.booksapp.Notifications;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.dianaivan.booksapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Diana Ivan on 1/6/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    final private static String TAG="MyFirebaseMessService";

    public void onMessageReceived(RemoteMessage remoteMsg)
    {
        Log.d(TAG,"Remote message from: "+remoteMsg.getFrom());
        Log.d(TAG,"Notification message body: "+remoteMsg.getNotification().getBody());

        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.book)
                .setContentTitle("Book Sharing App")
                .setContentText(remoteMsg.getNotification().getBody());

        int mNotificationId=001;
        NotificationManager mNotifMan=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        mNotifMan.notify(mNotificationId,mBuilder.build());
    }
}
