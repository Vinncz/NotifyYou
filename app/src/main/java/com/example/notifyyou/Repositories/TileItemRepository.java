package com.example.notifyyou.Repositories;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.Utils.CONFIG;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class TileItemRepository {
    private static SharedPreferences db;
    private static SharedPreferences.Editor editor;
    private static Gson g = new GsonBuilder().setPrettyPrinting().create();
    private static Type ArrayListWithTileItemType = new TypeToken<ArrayList<TileItem>>() {}.getType();

    public TileItemRepository (Context _context) {
        db = _context.getSharedPreferences(CONFIG.channelName, Context.MODE_PRIVATE);
        editor = db.edit();
    }

    public ArrayList<TileItem> GetAllTiles () {
        ArrayList<TileItem> tis = new ArrayList<>();

        tis.addAll(GetAllPinned());
        tis.addAll(GetNonPinned());

        return tis;
    }

    public ArrayList<TileItem> GetAllPinned () {
        return FromJson(
                Retrieve(CONFIG.sharedPreferencePinnedTileItemsChannel, ""),
                ArrayListWithTileItemType,
                new ArrayList<TileItem>()
        );
    }

    public ArrayList<TileItem> GetNonPinned () {
        return FromJson(
                Retrieve(CONFIG.sharedPreferenceUnpinnedTileItemsChannel, ""),
                ArrayListWithTileItemType,
                new ArrayList<TileItem>()
        );
    }

    public TileItem Get (int _tileId) {

        return null;
    }

    /*
     * (!) WARNING
     *
     * YANG BERTANGGUNG JAWAB UNTUK MEMASTIKAN BAHWA
     * VALUE BARU TIDAK MENG-OVERWRITE YANG LAMA
     * ADALAH MEREKA YANG MEMANGGIL METHOD PUT.
     */
    public Boolean Post (TileItem ti) {
        String json = ToJson(ti);

        Log("NEWLY SUBMITTED DATA:\n" + json);

        ArrayList<TileItem> existingItems = GetNonPinned();

        existingItems.add(ti);

        json = ToJson(existingItems);

        Write(CONFIG.sharedPreferenceUnpinnedTileItemsChannel, json);

        return CommitChanges();
    }

    private void Log (String _s) {
        System.out.println("\n\n" + _s + "\n\n");
    }

    private void Log (String _s, String _whoCalled) {
        System.out.println(_whoCalled + " called; \n\n" + _s + "\n\n");
    }

    private <T> String ToJson (Object _value) {
        return g.toJson(_value);
    }

    private <T> T FromJson (String _value, Type _class, Object _fallbackValue) {
        Object o = g.fromJson(_value, _class);
        return (T) (o != null ? o : _fallbackValue);
    }

    private String Retrieve (String _resourcePath, Object _fallbackValue) {
        /*
        *
        * RETRIEVAL SEQUENCE FOR DATA THAT ARE INSIDE SHARED PREFERENCES:
        *
        * 1. Look for data stored in the specified path, and retrieve it.
        *      (Here, the specified path is represented by the `_resourcePath` param)
        *
        * 2. Due to the saved data being encoded in JSON, it needs to be unwrapped first into a string.
        *      (Here, you call the FromJson() method to convert the retrieved JSON into a String object)
        *
        * 3. You return the converted value.
        */
        return FromJson (
                    db.getString(_resourcePath, ToJson(_fallbackValue)),
                    String.class,
                    _fallbackValue
               );
    }

    private <T> void Write (String _placementPath, String _value) {

        /* TODO: HERE SHOULD NOT BE ANYMORE VALIDATION BEYOND THIS POINT */

        /* Sanitize the data */
        String preparedData;
        try {
            /* If the provided data is correct, then the assignment should proceed normally */
            preparedData = ToJson(_value);
        } catch (Error e) {
            /* If its throwing an Error, then you know that the param you receive is invalid, and you must terminate the SP writing */
            Log("CAUGHT ERROR WHEN WRITING INTO SHARED PREFERENCE" + e.toString());
//            throw e;

            return;
        }

        /* Put the data into SharedPreference */
        editor.putString(_placementPath, preparedData);
    }

    public Boolean CommitChanges () {
        return editor.commit();
    }

}
