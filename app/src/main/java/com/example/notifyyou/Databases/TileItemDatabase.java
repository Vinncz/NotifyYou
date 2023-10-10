package com.example.notifyyou.Databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.ViewModels.TileItemDAO;

@Database(entities = {TileItem.class}, version = 1)
public abstract class TileItemDatabase extends RoomDatabase {

    private static TileItemDatabase instance;
    public abstract TileItemDAO tileItemDAO();

    public static synchronized TileItemDatabase getInstance(Context _context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                                _context.getApplicationContext(),
                                TileItemDatabase.class,
                                "tile_item_database"
                       ) .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }

        return instance;
    }

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate (@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private final TileItemDAO tiDAO;

        private PopulateDbAsyncTask(TileItemDatabase db) {
            tiDAO = db.tileItemDAO();
        }

        @Override
        protected Void doInBackground (Void... voids) {
            tiDAO.insert(new TileItem("You can pin this TileItem!", "Amazing, isn't it?"));
            return null;
        }
    }

}
