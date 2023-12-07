package com.example.notifyyou.Receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.notifyyou.Controllers.NotificationController;
import com.example.notifyyou.Factories.NotificationFactory;
import com.example.notifyyou.R;
import com.example.notifyyou.Utils.CONFIG;
import com.example.notifyyou.Views.Activities.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
        int id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");

//        AudioAttributes attributes = new AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .setUsage(AudioAttributes.USAGE_ALARM)
//                .build();
//        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        NotificationChannel channel = new NotificationChannel(CONFIG.channelId, CONFIG.channelName, NotificationManager.IMPORTANCE_HIGH);
//        channel.setSound(ringtone, attributes);
//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.createNotificationChannel(channel);
//
//        NotificationController nc = new NotificationController(NotificationManagerCompat.from(context));
//        Notification n = NotificationFactory.CreatePersistentNotificationForDefaultChannelId(context, title, body);
//        nc.Notify(id, n);

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();
        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationChannel channel = new NotificationChannel("AlarmChannelID", "TileItem Alarm", NotificationManager.IMPORTANCE_HIGH);
        channel.setSound(ringtone, attributes);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        NotificationController nc = new NotificationController(NotificationManagerCompat.from(context));

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 1, i, PendingIntent.FLAG_MUTABLE);
        Notification n = new NotificationCompat.Builder(context, "AlarmChannelID")
                .setCategory(Notification.CATEGORY_ALARM)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.app_icon)
                .setContentIntent(pi)
                .build();
        nc.Notify(id, n);
    }
}
