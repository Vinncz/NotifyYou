package com.example.notifyyou.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {

    private NotificationManager m;
    private Activity a;

    public NotificationHelper (NotificationManager _m, Activity _a) {
        this.m = _m;
        this.a = _a;

        InitializeDefaultNotificationChannel();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void CheckPermission () {
        NotificationManagerCompat nm = NotificationManagerCompat.from(this.a);

        if (ActivityCompat.checkSelfPermission(this.a, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this.a, Manifest.permission.POST_NOTIFICATIONS)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this.a);
                builder.setTitle("Permission Needed")
                        .setMessage("Post Notifications")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions(a, new String[] {Manifest.permission.POST_NOTIFICATIONS}, 1);
                            }
                        });
                builder.create().show();

            } else {
                ActivityCompat.requestPermissions(this.a, new String [] {Manifest.permission.POST_NOTIFICATIONS}, 1);
            }

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
