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
        // masih gapaham kenapa perlu di retrieve sebagai string dulu
        String tis = Retrieve(CONFIG.sharedPreferencePinnedTileItemsChannel, "", String.class);
        Log("YANG DIDAPET DARI GETALLPINNED:\n" + tis);

        return FromJson(tis, ArrayListWithTileItemType, new ArrayList<TileItem>());
    }

    public ArrayList<TileItem> GetNonPinned () {
        // masih gapaham kenapa perlu di retrieve sebagai string dulu
        String tis = Retrieve(CONFIG.sharedPreferenceUnpinnedTileItemsChannel, "", String.class);
        Log("YANG DIDAPET DARI SHAREDPREFERENCES:\n" + tis);

        ArrayList items = FromJson(tis, ArrayListWithTileItemType, new ArrayList<TileItem>());
        Log("KALO [YANG DIDAPET TADI] DIUBAH DARI [JSON] KE [OBJECTNYA]:\n" + ToJson(items));

        return items;
    }

    public TileItem Get (int _tileId) {

        return null;
    }

    public Boolean Post (TileItem ti) {
        String json = ToJson(ti);
        Log("YANG BARU DISUBMIT:\n" + json);

        ArrayList<TileItem> existingItems = GetNonPinned();

        existingItems.add(ti);

        json = ToJson(existingItems);

        return Put(CONFIG.sharedPreferenceUnpinnedTileItemsChannel, json);
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

    private <T> T FromJson (String _value, Type _class) {
        return g.fromJson(_value, _class);
    }

    private <T> T FromJson (String _value, Type _class, Object _fallbackValue) {
        Object o = g.fromJson(_value, _class);
        return (T) (o != null ? o : _fallbackValue);
    }

    private <T> T Retrieve (String _resourcePath, Object _fallbackValue, Type _resourceClass) {
        String retrievedData = db.getString(_resourcePath, ToJson(_fallbackValue));
        return FromJson(retrievedData, _resourceClass);
    }

    private <T> Boolean Put (String _placementPath, String _value) {
        /* Sanitize the data */
        String preparedData;
        try { preparedData = ToJson(_value); } catch (Error e) { System.out.println(e.toString()); return false;}

        /* Put the data into SharedPreference */
        editor.putString(_placementPath, preparedData);

        return editor.commit();
    }

}
