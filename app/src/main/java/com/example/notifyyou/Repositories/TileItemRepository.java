package com.example.notifyyou.Repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import com.example.notifyyou.Databases.TileItemDatabase;
import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.DataAccessObjects.TileItemDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TileItemRepository {
    private TileItemDAO dao;
    private LiveData<List<TileItem>> allTileItems;


    /* Asynchronous Tasks Handler */
    ExecutorService es = Executors.newSingleThreadExecutor();
    Handler h = new Handler(Looper.getMainLooper());


    public TileItemRepository(Application _app) {
        TileItemDatabase db = TileItemDatabase.getInstance(_app);
        dao = db.dao();

        allTileItems = dao.getAll();
    }


    /* GET */
    public LiveData<TileItem> getLatest () {
        return dao.getLatest();
    }
    public LiveData<TileItem> get (Integer _id) {
        return dao.get(_id);
    }
    public LiveData<List<TileItem>> getAll () {
        return dao.getAll();
    }
    public LiveData<List<TileItem>> getAllPinned () { return dao.getAllPinned(); }
    public LiveData<List<TileItem>> getAllUnpinned () { return dao.getAllUnpinned(); }


    /* POST */
    public void insert (TileItem _ti) {
        es.execute(() -> dao.insert(_ti));
    }


    /* PATCH */
    public void update (TileItem _ti) {
        es.execute(() -> dao.update(_ti));
    }


    /* DELETE */
    public void delete (TileItem _ti) {
        es.execute(() -> dao.delete(_ti));
    }
    public void deleteAll () {
        es.execute(() -> dao.deleteAll());
    }

}
