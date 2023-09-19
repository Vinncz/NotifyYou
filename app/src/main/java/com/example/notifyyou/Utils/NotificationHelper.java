package com.example.notifyyou.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationHelper {

    private NotificationManager m;

    public NotificationHelper (NotificationManager _m) {
        this.m = _m;
    }

    public void CreateNotificationChannel (String _channelId, String _channelName, int _notificationManagerImportanceLevel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            NotificationChannel channel = new NotificationChannel(_channelId, _channelName, _notificationManagerImportanceLevel);
            VerifyManagerExistence();
            m.createNotificationChannel(channel);

        }
    }

    private Boolean VerifyManagerExistence () {
        Boolean flag = true;

        if (this.m == null) {
            flag = false;
        }

        return flag;
    }

}
