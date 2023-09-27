package com.example.notifyyou.Factories;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.example.notifyyou.R;
import com.example.notifyyou.Utils.CONFIG;

public class NotificationFactory {

    public static Notification CreatePersistentNotification (Context _c, String _notificationChannelId, String _title, String _body) {
        return new NotificationCompat.Builder(_c, _notificationChannelId)
                .setOngoing(true)
                .setContentTitle(_title)
                .setContentText(_body)
                .setSmallIcon(R.drawable.app_icon)
                .build();
    }

    public static Notification CreatePersistentNotificationForDefaultChannelId (Context _c, String _title, String _body) {
        return new NotificationCompat.Builder(_c, CONFIG.channelId)
                .setOngoing(true)
                .setContentTitle(_title)
                .setContentText(_body)
                .setSmallIcon(R.drawable.app_icon)
                .build();
    }


    public static Notification CreateNotification (Context _c, String _notificationChannelId, String _title, String _body) {
        return new NotificationCompat.Builder(_c, _notificationChannelId)
                .setContentTitle(_title)
                .setContentText(_body)
                .setSmallIcon(R.drawable.app_icon)
                .build();
    }

}
