package com.example.notifyyou.Controllers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.Repositories.TileItemRepositoryOLD;

import java.util.ArrayList;

public class TileItemController {

    private static TileItemRepositoryOLD tir;

    public TileItemController (Context _context) {
        tir = new TileItemRepositoryOLD(_context);
    }



    public ArrayList<TileItem> GetAllTiles () {
        return tir.GetAllTiles();
    }

    public ArrayList<TileItem> GetAllPinned () {
        return tir.GetAllPinned();
    }

    public ArrayList<TileItem> GetNonPinned () {
        return tir.GetNonPinned();
    }



    public Boolean validate (String _title, String _body) {
        return checkTitle(_title) && checkBody(_body);
    }

    @NonNull
    private Boolean checkTitle (String _t) {
        boolean flag = true;

        /* _t is not null and is not empty */
        flag = _t != null && !_t.isEmpty() && !_t.isBlank() && _t.trim().length() >= 3;

        return flag;
    }

    @NonNull
    private Boolean checkBody (String _b) {
        boolean flag = true;

        flag = _b != null && !_b.isEmpty() && !_b.isBlank() && _b.trim().length() >= 3;

        return flag;
    }

}
