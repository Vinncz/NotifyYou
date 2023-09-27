package com.example.notifyyou.Repositories;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.Utils.CONFIG;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class TileItemRepository {
    private static SharedPreferences db;
    private static SharedPreferences.Editor editor;
    private static Gson g = new GsonBuilder().setPrettyPrinting().create();

    public TileItemRepository (Context _context) {
        db = _context.getSharedPreferences(CONFIG.channelName, Context.MODE_PRIVATE);
        editor = db.edit();
    }

    public ArrayList<TileItem> GetAllTiles () {
        ArrayList<TileItem> tis = new ArrayList<>();

        tis.add(new TileItem("Do chores", "Wash dishes, do laundry, take trash out"));
        tis.add(new TileItem("Defrost tomorrow's lunch", "Don't forget this!"));
        tis.add(new TileItem("Follow up with SSC", "Request an internship letter"));
        tis.add(new TileItem("Do assignments", "AOL Framework Layer Architecture, AOL Operating System, AOL Mobile Programming, Make a prototype for Entrepreneurship Market Validation, Final Project for OOAD LAB"));

        return tis;
    }

    public ArrayList<TileItem> GetAllPinned () {
        ArrayList<TileItem> tis = new ArrayList<>();

        String s = Retrieve(CONFIG.sharedPreferencePinnedTileItemsChannel, tis, String.class);
        tis.add(FromJson(s, TileItem.class));
//        tis.add(new TileItem("Do chores", "Wash dishes, do laundry, take trash out"));
//        tis.add(new TileItem("Defrost tomorrow's lunch", "Don't forget this!"));
//        tis.add(new TileItem("Follow up with SSC", "Request an internship letter"));
//        tis.add(new TileItem("Do assignments", "AOL Framework Layer Architecture, AOL Operating System, AOL Mobile Programming, Make a prototype for Entrepreneurship Market Validation, Final Project for OOAD LAB"));

        return tis;
    }

    public ArrayList<TileItem> GetNonPinned () {
        ArrayList<TileItem> tis = new ArrayList<>();

        String s = Retrieve(CONFIG.sharedPreferenceUnpinnedTileItemsChannel, tis, String.class);
        tis.add(FromJson(s, TileItem.class));
//        tis.add(new TileItem("Non-pinned TileItem: Do chores", "Wash dishes, do laundry, take trash out"));
//        tis.add(new TileItem("Non-pinned TileItem: Defrost tomorrow's lunch", "Don't forget this!"));
//        tis.add(new TileItem("Non-pinned TileItem: Follow up with SSC", "Request an internship letter"));
//        tis.add(new TileItem("Non-pinned TileItem: Do assignments", "AOL Framework Layer Architecture, AOL Operating System, AOL Mobile Programming, Make a prototype for Entrepreneurship Market Validation, Final Project for OOAD LAB"));

        return tis;
    }

    public TileItem Get (int _tileId) {

        return null;
    }

    public Boolean Post (TileItem ti) {
        String json = ToJson(ti);
        System.out.println("\n\n" + json);

        TileItem fromJson = FromJson(json, TileItem.class);

        /* NOT DONE
        *
        *  Value lama harus diappend ke arraylist
        *  arraylsit itu nntinya disimpen lagi
        *  */

        Put(CONFIG.sharedPreferenceUnpinnedTileItemsChannel, json);

        return true;
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

    private <T> T FromJson (String _value, Class<T> _class) {
        Log(_value, "FromJSON");
        return g.fromJson(_value, _class);
    }

    private <T> T Retrieve (String _resourcePath, Object _fallbackValue, Class<T> _resourceClass) {
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
