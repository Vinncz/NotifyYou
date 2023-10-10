package com.example.notifyyou.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.notifyyou.Databases.TileItemDatabase;
import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.ViewModels.TileItemDAO;

import java.util.ArrayList;

public class TileItemRepository {
    private TileItemDAO tileItemDAO;
    private LiveData<ArrayList<TileItem>> pinedTileItems;
    private LiveData<ArrayList<TileItem>> unpinedTileItems;


    public TileItemRepository(Application _app) {
        TileItemDatabase db = TileItemDatabase.getInstance(_app);
        tileItemDAO = db.tileItemDAO();

        pinedTileItems = tileItemDAO.getAllPinned();
        unpinedTileItems = tileItemDAO.getAllUnpinned();
    }


    /* GET */
    public LiveData<ArrayList<TileItem>> getPinedTileItems () {
        return pinedTileItems;
    }
    public LiveData<ArrayList<TileItem>> getUnpinedTileItems () {
        return unpinedTileItems;
    }


    /* POST */
    public void insert (TileItem _ti) {
        new InsertTileItemAsyncTask(tileItemDAO).execute(_ti);
    }
    private static class InsertTileItemAsyncTask extends AsyncTask<TileItem, Void, Void> {
        private TileItemDAO tiDAO;

        private InsertTileItemAsyncTask (TileItemDAO _tiDAO) {
            this.tiDAO = _tiDAO;
        }

        @Override
        protected Void doInBackground (TileItem... tileItems) {
            tiDAO.insert(tileItems[0]);
            return null;
        }
    }


    /* PATCH */
    public void update (TileItem _ti) {
        new UpdateTileItemAsyncTask(tileItemDAO).execute(_ti);
    }
    private static class UpdateTileItemAsyncTask extends AsyncTask<TileItem, Void, Void> {
        private TileItemDAO tiDAO;

        private UpdateTileItemAsyncTask (TileItemDAO _tiDAO) {
            this.tiDAO = _tiDAO;
        }

        @Override
        protected Void doInBackground (TileItem... tileItems) {
            tiDAO.update(tileItems[0]);
            return null;
        }
    }


    /* DELETE */
    public void delete (TileItem _ti) {
        new DeleteTileItemAsyncTask(tileItemDAO).execute(_ti);
    }
    public void deleteAll () {
        new DeleteAllTileItemAsyncTask(tileItemDAO).execute();
    }

    private static class DeleteTileItemAsyncTask extends AsyncTask<TileItem, Void, Void> {
        private TileItemDAO tiDAO;

        private DeleteTileItemAsyncTask (TileItemDAO _tiDAO) {
            this.tiDAO = _tiDAO;
        }

        @Override
        protected Void doInBackground (TileItem... tileItems) {
            tiDAO.delete(tileItems[0]);
            return null;
        }
    }
    private static class DeleteAllTileItemAsyncTask extends AsyncTask<Void, Void, Void> {
        private TileItemDAO tiDAO;

        private DeleteAllTileItemAsyncTask (TileItemDAO _tiDAO) {
            this.tiDAO = _tiDAO;
        }

        @Override
        protected Void doInBackground (Void... voids) {
            tiDAO.deleteAll();
            return null;
        }
    }

}
