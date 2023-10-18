package com.example.notifyyou.Utils.Permissions;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.notifyyou.Utils.CONFIG;

public class NotificationHelper {

    private NotificationManager m;
    private Activity a;

    public NotificationHelper (NotificationManager _m, Activity _a) {
        this.m = _m;
        this.a = _a;

        InitializeDefaultNotificationChannel();
    }

    public static void requestNotificationPolicyAccess (Context context, OnPermissionResultListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (!notificationManager.isNotificationPolicyAccessGranted()) {
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, PermissionManager.PERMISSION_REQUEST_CODE);
                }

            } else {
                listener.onPermissionGranted("android.permission.SEND_NOTIFICATIONS");

            }

        } else {
            listener.onPermissionGranted("android.permission.SEND_NOTIFICATIONS");

        }

    }

    public void CreateNotificationChannel (String _channelId, String _channelName, int _notificationManagerImportanceLevel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            NotificationChannel channel = new NotificationChannel(_channelId, _channelName, _notificationManagerImportanceLevel);
            m.createNotificationChannel(channel);

        }
    }

    public void InitializeDefaultNotificationChannel () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            NotificationChannel channel = new NotificationChannel(CONFIG.channelId, CONFIG.channelName, NotificationManager.IMPORTANCE_HIGH);
            m.createNotificationChannel(channel);

        }
    }

}
