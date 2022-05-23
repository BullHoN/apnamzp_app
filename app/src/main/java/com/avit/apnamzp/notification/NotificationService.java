package com.avit.apnamzp.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.avit.apnamzp.HomeActivity;
import com.avit.apnamzp.R;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class NotificationService extends FirebaseMessagingService {

    private String TAG = "FirebaseNotification";
    public static final String CHANNEL_ORDER_ID = "OrdersStatusChannel";
    public static final String CHANNEL_OFFERS_ID = "OffersChannel";
    public static final String CHANNEL_NEWS_ID = "NewsChannel";
    private NotificationManagerCompat notificationManager;
    public static int ORDER_NOTIFICATION_ID = 1;

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
        if(notificationType.contains("order_status")){

            String title = data.get("title");
            String desc = data.get("desc");
            String orderId = data.get("orderId");

            manageOrderNotification(title,desc,orderId);
        }


    }

    private void manageOrderNotification(String title,String desc,String orderId){
        if(NotificationUtils.isAppIsInBackground(getApplicationContext())){
            showOrderNotification(title,desc,orderId);
        }
        else {
            Intent intent = new Intent();
            intent.setAction("com.avit.apnamzp.ORDER_STATUS_UPDATE");

            intent.putExtra("orderId",orderId);
            getApplicationContext().sendBroadcast(intent);

        }
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

    private void showOrderNotification(String title,String desc,String orderId){

        Intent viewOrderDetailsIntent = new Intent(getApplicationContext(), HomeActivity.class);
        viewOrderDetailsIntent.putExtra("orderId",orderId);
        viewOrderDetailsIntent.setAction("com.avit.apnamzp_order_status_update");

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,viewOrderDetailsIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ORDER_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.main_icon)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .build();

        if(ORDER_NOTIFICATION_ID > 1073741824){
            ORDER_NOTIFICATION_ID = 0;
        }

        notificationManager.notify(ORDER_NOTIFICATION_ID++,notification);
    }

    private void showOffersNotification(){

    }

    private void showNewsNotification(){

    }

}