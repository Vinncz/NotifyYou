package com.example.notifyyou.DataAccessObjects;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notifyyou.Models.TileItem;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TileItemDAO {

    @Query("SELECT * FROM tile_items ORDER BY id DESC LIMIT 1")
    LiveData<TileItem> getLatest ();

    @Query("SELECT * FROM tile_items WHERE id = :id LIMIT 1")
    LiveData<TileItem> get (Integer id);

    @Query("SELECT * FROM tile_items ORDER BY modifiedAt")
    LiveData<List<TileItem>> getAll ();

    @Query("SELECT * FROM tile_items WHERE isPinned = 1")
    LiveData<List<TileItem>> getAllPinned ();

    @Query("SELECT * FROM tile_items WHERE isPinned = 0")
    LiveData<List<TileItem>> getAllUnpinned ();

    @Insert
    void insert (TileItem ti);

    @Update
    void update (TileItem ti);

    @Delete
    void delete (TileItem ti);

    @Query("DELETE FROM tile_items")
    void deleteAll();

}
