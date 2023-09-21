package com.example.notifyyou.Repositories;

import android.content.SharedPreferences;

import com.example.notifyyou.Models.TileItem;

import java.util.ArrayList;

public class TileItemRepository {

    public static ArrayList<TileItem> GetAllTiles () {
        ArrayList<TileItem> tis = new ArrayList<>();
        tis.add(new TileItem("what the fuck", "what the actual fuck"));
        tis.add(new TileItem("hey", "kamu"));
        tis.add(new TileItem("knock knock", "who's there?"));

        return tis;
    }

    public static TileItem Get (int _tileId) {
//        return TileItem::find(_tileId);
        return null;
    }

    public static Boolean Post (TileItem ti) {
        return true;
    }

}
