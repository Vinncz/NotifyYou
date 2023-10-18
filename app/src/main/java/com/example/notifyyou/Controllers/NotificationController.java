package com.example.notifyyou.Controllers;

import android.annotation.SuppressLint;
import android.app.Notification;

import androidx.core.app.NotificationManagerCompat;

public class NotificationController {

    private static NotificationManagerCompat manager;

    public NotificationController (NotificationManagerCompat _m) {
        this.manager = _m;
    }

    @SuppressLint("MissingPermission")
    public void Notify (Integer _notificationId, Notification _n) {
        manager.notify(_notificationId, _n);

    }

    public void Cancel (Integer _notificationId) {
        manager.cancel(_notificationId);
    }
}
