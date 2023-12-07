package com.example.notifyyou.Controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.Receiver.AlarmReceiver;
import com.example.notifyyou.Repositories.TileItemRepositoryOLD;

import java.util.ArrayList;
import java.util.Calendar;

public class TileItemController {

    private static TileItemRepositoryOLD tir;

    public TileItemController (Context _context) {
        tir = new TileItemRepositoryOLD(_context);
    }

    public TileItemController(){}

    public ArrayList<TileItem> GetAllTiles () {
        return tir.GetAllTiles();
    }

    public ArrayList<TileItem> GetAllPinned () {
        return tir.GetAllPinned();
    }

    public ArrayList<TileItem> GetNonPinned () {
        return tir.GetNonPinned();
    }



    public Boolean validate (String _title, String _body) {
        return checkTitle(_title) && checkBody(_body);
    }

    @NonNull
    private Boolean checkTitle (String _t) {
        boolean flag = true;

        /* _t is not null and is not empty */
        flag = _t != null && !_t.isEmpty() && !_t.isBlank() && _t.trim().length() >= 3;

        return flag;
    }

    @NonNull
    private Boolean checkBody (String _b) {
        boolean flag = true;

        flag = _b != null && !_b.isEmpty() && !_b.isBlank() && _b.trim().length() >= 3;

        return flag;
    }

    /* MANAGE ALARM */
    public void checkAlarm(TileItem ti, Context context){
        //Check if the alarm need to be turned on or off
        if(ti.getAlarmIsActive()){
            setAlarm(ti, context);
            Toast.makeText(context, "Your alarm is turned on", Toast.LENGTH_SHORT).show();
        }else {
            cancelAlarm(ti, context);
            Toast.makeText(context, "Your alarm is turned off", Toast.LENGTH_SHORT).show();
        }
    }

    public void setAlarm(TileItem ti, Context context){
        // Set the alarm when the alarm is active.
        int hour = Integer.parseInt(ti.getSelectedTimeForAlarm().substring(0,2));
        int minute = Integer.parseInt(ti.getSelectedTimeForAlarm().substring(3,5));
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, hour);
        alarmTime.set(Calendar.MINUTE, minute);
        alarmTime.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("id", ti.getId());
        intent.putExtra("title", ti.getTitle());
        intent.putExtra("body", ti.getBody());

        if (alarmTime.before(Calendar.getInstance())){
            alarmTime.add(Calendar.DATE, 1);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ti.getId(), intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm(TileItem ti, Context context){
        // Cancel the alarm when the alarm is off.
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ti.getId(), intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
    }
}
