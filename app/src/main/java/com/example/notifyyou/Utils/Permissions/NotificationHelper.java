package com.example.notifyyou.Utils.Permissions;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notifyyou.R;
import com.example.notifyyou.Utils.CONFIG;

public class NotificationHelper {

    private NotificationManager notificationManager;
    private AppCompatActivity activityInstance;

    public NotificationHelper (NotificationManager _m, AppCompatActivity _a) {
        this.notificationManager = _m;
        this.activityInstance = _a;

        InitializeDefaultNotificationChannel();
    }

    @Deprecated
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

    public void InitializeDefaultNotificationChannel () {
        if ( activityInstance != null ) {
            ActivityResultLauncher<String> permissionLauncherSingle = activityInstance.registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),

                    userGrantsNotificationPermission -> {
                        if ( userGrantsNotificationPermission ) {
                            Toast.makeText(activityInstance, R.string.notification_permission_granted, Toast.LENGTH_SHORT).show();
                            Toast.makeText(activityInstance, R.string.persuade_for_notification_permission, Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(activityInstance, R.string.notification_permission_dismissed, Toast.LENGTH_SHORT).show();

                        }
                    }
            );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                /* ask the user for permission */
                permissionLauncherSingle.launch("android.permission.POST_NOTIFICATIONS");
                NotificationChannel channel = new NotificationChannel(CONFIG.channelId, CONFIG.channelName, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);

            } else {
                NotificationChannel channel = new NotificationChannel(CONFIG.channelId, CONFIG.channelName, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);

            }

        }
    }

}
