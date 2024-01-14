package com.oytuntekesin.authenticationapp.helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.oytuntekesin.authenticationapp.R;

public class NotificationHelper {

    private static final String CHANNEL_ID = "glyco_check";
    private static final String CHANNEL_NAME = "GlycoCheck";
    private static final int NOTIFICATION_ID = 1;

    public static void showNotification(Context context, String title, String message) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Android Oreo ve sonraki sürümler için bildirim kanalı oluştur
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Bildirim oluştur
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_glyco)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.FLAG_BUBBLE);

        // Bildirimi göster
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}