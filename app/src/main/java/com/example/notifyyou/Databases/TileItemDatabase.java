package com.example.notifyyou.Databases;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.notifyyou.Databases.Utils.DbSeeder;
import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.Utils.CONFIG;
import com.example.notifyyou.DataAccessObjects.TileItemDAO;

@Database(entities = {TileItem.class}, version = 1, exportSchema = false)
public abstract class TileItemDatabase extends RoomDatabase {

    private static Context context;
    private static TileItemDatabase instance;
    public abstract TileItemDAO dao();

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate (@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);

        seedDatabase(context);
        }
    };


    /* Singleton Pattern */
    public static synchronized TileItemDatabase getInstance(Context _context) {
        context = _context;
        if (instance == null) {
            instance = Room.databaseBuilder(
                            _context.getApplicationContext(),
                            TileItemDatabase.class,
                            CONFIG.databaseName
                       ) .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }

        return instance;
    }


    private static void seedDatabase (@NonNull Context _context) {
        SharedPreferences sp = _context.getSharedPreferences(CONFIG.sharedPreferenceDatabaseSeedChannel, Context.MODE_PRIVATE);
        boolean dbHasBeenSeeded = sp.getBoolean(CONFIG.sharedPreferenceArgument_DatabaseIsSeeded, true);

        if ( !dbHasBeenSeeded ) {
            new DbSeeder<TileItemDatabase>(instance) {
                @Override
                public void onCreate () {
                    db.dao().insert(new TileItem("Welcome to NotifyYou!", "Feel free to do whatever with these cards!", true));
                    db.dao().insert(new TileItem("Do Chores", "+ Wash dishes\n+ Take out trash\n+ Vacuum ploor\n+ Water plants"));
                    db.dao().insert(new TileItem("Do Tasks", "+ Do mathematics\n+ Do physics"));
                }

                @Override
                public void onFinishing () {
//                    Toast.makeText(_context, "Finished Seeding the Database!", Toast.LENGTH_SHORT).show();
                }
            };
        }
    }

}
