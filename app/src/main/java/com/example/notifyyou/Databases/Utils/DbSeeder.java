package com.example.notifyyou.Databases.Utils;

import android.os.Handler;
import android.os.Looper;

import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class DbSeeder <T extends RoomDatabase> {
    public ExecutorService es = Executors.newSingleThreadExecutor();
    protected T db;

    public DbSeeder (T _databaseInstance) {
        this.db = _databaseInstance;
        seedDbAsynchronously();
    }


    public void seedDbAsynchronously () {
        es.execute(() -> {
            onCreate();
            onFinishing();
        });
    }

    public abstract void onCreate();
    public abstract void onFinishing();

}
