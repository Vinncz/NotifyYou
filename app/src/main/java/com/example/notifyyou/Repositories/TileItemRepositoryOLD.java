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

public class TileItemRepositoryOLD {
    private static SharedPreferences db;
    private static SharedPreferences.Editor editor;
    private static final Gson g = new GsonBuilder().setPrettyPrinting().create();
    private static final Type ArrayListWithTileItemType = new TypeToken<ArrayList<TileItem>>() {}.getType();

    /*
    * CACHE HANYA AKAN MENJADI INVALID
    * JIKA DAN HANYA JIKA ADA PERUBAHAN PADA
    * SHAREDPREFERENCES DENGAN KEY CONFIG.sharedPreferencePinnedTileItemsChannel
    * DAN DENGAN KEY CONFIG.sharedPreferenceUnpinnedTileItemsChannel
    *
    * MAKA DARI ITU:
    * - SETIAP METHOD YANG MENGUBAH DATA DI SHAREDPREFERENCES
    *   HARUS KOORDINASI DENGAN GLOBAL VARIABLE cacheIsValid.
    *   (DELTE, UPDATE, CREATE)
    * - SETIAP METHOD YANG MENGUBAH DATA DI SHAREDPREFERENCES
    *   HANYA BISA MENGUBAH VARIABLE cacheIsValid MENJADI FALSE
    * - SETIAP METHOD YANG MEMBACA DATA DARI SHAREDPREFERENCES
     *   HANYA BISA MENGUBAH VARIABLE cacheIsValid MENJADI TRUE
    */
    private ArrayList<TileItem> cachedTiles;
    private boolean cacheIsValid = false;



    public TileItemRepositoryOLD (Context _context) {
        db = _context.getSharedPreferences(CONFIG.channelName, Context.MODE_PRIVATE);
        editor = db.edit();
    }

    public ArrayList<TileItem> GetAllTiles () {
        if (cacheIsValid) return cachedTiles;

        ArrayList<TileItem> tis = new ArrayList<>();

        tis.addAll(GetAllPinned());
        tis.addAll(GetNonPinned());

        cachedTiles = tis;
        cacheIsValid = true;

        return tis;
    }

    public ArrayList<TileItem> GetAllPinned () {
        if (cacheIsValid) return cachedTiles;

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

        /*
            UNTUK MEMPERCEPAT METHOD GET() DARI SETIAP KALI MEMBACA SHARED PREFERENCES
            DAN MELAKUKAN LINEAR SEARCH DARI SETIAP RECORDS UNTUK MENCARI ID YANG SAMA DENGAN _tileID:

            1. buat array baru yang hanya simpen ID milik TileItems.
            2. kalo mau Get() suatu TileItem, cari di lookup array apakah id tersebut exists.
            3. jika ada, lihat di index berapa ID tersebut tersimpan pada array, dan tinggal lookup ke JSON tree-nya sendiri.
                 (misal id "1" berada di index ke-2, maka tinggal GetAllTiles().get(2) <-- dimana 2 adalah index ke-n )
                 (ada perlunya jika hasil dari GetAllTiles disimpan ke variabel untuk caching)
            4. jika tdk ada, throw error.

            public List<String> getValuesForGivenKey(String jsonArrayStr, String key) {
                JSONArray jsonArray = new JSONArray(jsonArrayStr);
                return IntStream.range(0, jsonArray.length())
                  .mapToObj(index -> ((JSONObject)jsonArray.get(index)).optString(key))
                  .collect(Collectors.toList());
            }
        */

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

    private String ToJson (Object _value) {
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

    private void Write (String _placementPath, String _value) {

        /* TODO: HERE SHOULD NOT BE ANYMORE VALIDATION BEYOND THIS POINT */

        /* Sanitize the data */
        String preparedData;
        try {
            /* If the provided data is correct, then the assignment should proceed normally */
            preparedData = ToJson(_value);
        } catch (Error e) {
            /* If its throwing an Error, then you know that the param you receive is invalid, and you must terminate the SP writing */
//            Log("CAUGHT ERROR WHEN WRITING INTO SHARED PREFERENCE" + e.toString());
            throw e;

        }

        /* Put the data into SharedPreference */
        editor.putString(_placementPath, preparedData);
        cacheIsValid = false;
    }

    public Boolean CommitChanges () {
        return editor.commit();
    }

}
