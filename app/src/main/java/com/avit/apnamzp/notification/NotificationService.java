package com.avit.apnamzp.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.avit.apnamzp.R;
import com.avit.apnamzp.localdb.User;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class NotificationService extends FirebaseMessagingService {

    private String TAG = "FirebaseNotification";
    public static final String CHANNEL_ORDER_ID = "OrdersStatusChannel";
    public static final String CHANNEL_OFFERS_ID = "OffersChannel";
    public static final String CHANNEL_NEWS_ID = "NewsChannel";
    private NotificationManagerCompat notificationManager;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        User.setFCMToken(getApplicationContext(),s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String,String> data =  remoteMessage.getData();

        notificationManager = NotificationManagerCompat.from(getApplicationContext());
        createNotificationChannels();

        String notificationType = data.get("type");
        showOrderNotification();

    }

    public void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel orderStatusChannel = new NotificationChannel(CHANNEL_ORDER_ID,"Orders Status Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            orderStatusChannel.setDescription("This channel is responsible for all the notification regarding your order status.");

            NotificationChannel offersChannel = new NotificationChannel(CHANNEL_OFFERS_ID,"Offers Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            orderStatusChannel.setDescription("This channel is responsible for all the notification regarding new exclusive offers on ApnaMZP");

            NotificationChannel newsChannel = new NotificationChannel(CHANNEL_NEWS_ID,"News Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            orderStatusChannel.setDescription("This channel is responsible for all the notification regarding what's new and recommendations");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(orderStatusChannel);
            manager.createNotificationChannel(offersChannel);
            manager.createNotificationChannel(newsChannel);

        }
    }

    private void showOrderNotification(){
        Notification notification = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ORDER_ID)
                .setContentTitle("Order title")
                .setSmallIcon(R.drawable.main_icon)
                .setContentText("Irder Message")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1,notification);
    }

    private void showOffersNotification(){

    }

    private void showNewsNotification(){

    }

}