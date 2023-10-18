package com.example.notifyyou.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.Repositories.TileItemRepository;

import java.util.ArrayList;
import java.util.List;

public class TileItemViewModel extends AndroidViewModel {
    private TileItemRepository repo;
    private LiveData<List<TileItem>> allTileItems;

    public TileItemViewModel (@NonNull Application _application) {
        super(_application);
        repo = new TileItemRepository(_application);

        allTileItems = repo.getAll();
    }

    /* GET */
    public LiveData<TileItem> getLatest () { return repo.getLatest(); }
    public LiveData<TileItem> get (Integer _id) { return repo.get(_id); }
    public LiveData<List<TileItem>> getAll () { return repo.getAll(); }
    public LiveData<List<TileItem>> getAllPinned () { return repo.getAllPinned(); }
    public LiveData<List<TileItem>> getAllUnpinned () { return repo.getAllUnpinned(); }


    /* POST */
    public void insert (TileItem _ti) {
        repo.insert(_ti);
    }


    /* PATCH */
    public void update (TileItem _ti) {
        repo.update(_ti);
    }


    /* DELETE */
    public void delete (TileItem _ti) {
        repo.delete(_ti);
    }
    public void deleteAll () {
        repo.deleteAll();
    }


}
