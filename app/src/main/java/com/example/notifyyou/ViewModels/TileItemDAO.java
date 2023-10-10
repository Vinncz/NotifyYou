package com.example.notifyyou.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notifyyou.Models.TileItem;

import java.util.ArrayList;

@Dao
public interface TileItemDAO {

    @Insert
    void insert (TileItem _ti);

    @Update
    void update (TileItem _ti);

    @Delete
    void delete (TileItem _ti);

    @Query("DELETE FROM tile_items")
    void deleteAll();

    @Query("SELECT * FROM tile_items WHERE isPinned = 1 ORDER BY modifiedAt DESC")
    LiveData<ArrayList<TileItem>> getAllPinned();

    @Query("SELECT * FROM tile_items WHERE isPinned = 0 ORDER BY modifiedAt DESC")
    LiveData<ArrayList<TileItem>> getAllUnpinned();
}
