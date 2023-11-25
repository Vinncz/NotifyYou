package com.example.notifyyou.Utils.Permissions;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notifyyou.Utils.CONFIG;

public class NotificationHelper {

    private NotificationManager m;
    private AppCompatActivity a;

    public NotificationHelper (NotificationManager _m, AppCompatActivity _a) {
        this.m = _m;
        this.a = _a;

        InitializeDefaultNotificationChannel();
    }

    public static void requestNotificationPolicyAccess (Context context, OnPermissionResultListener listener) {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
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
        if (a != null) {
            ActivityResultLauncher<String> permissionLauncherSingle = a.registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult (Boolean granted) {

                            if (granted) {
                                NotificationChannel channel = new NotificationChannel(CONFIG.channelId, CONFIG.channelName, NotificationManager.IMPORTANCE_HIGH);
                                m.createNotificationChannel(channel);

                                Toast.makeText(a, "Notification is permitted", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(a, "Notification is blocked! You won't receive any notifications from us!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
            );
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && a != null) {
                permissionLauncherSingle.launch("android.permission.POST_NOTIFICATIONS");
            } else {
                NotificationChannel channel = new NotificationChannel(CONFIG.channelId, CONFIG.channelName, NotificationManager.IMPORTANCE_HIGH);
                m.createNotificationChannel(channel);
            }
        }
    }

}
