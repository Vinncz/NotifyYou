package com.example.notifyyou.Factories;

import com.example.notifyyou.Models.TileItem;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TileItemFactory {

    public static TileItem MakeOne (String _title, String _body) {
        TileItem ti = new TileItem(_title, _body);

        return ti;
    }

    public static TileItem MakeOneWithAlarm (String _title, String _body, String _selectedTimeForAlarm) {
        TileItem ti = new TileItem(_title, _body);
        ti.setAlarmIsActive(true);
        ti.setSelectedTimeForAlarm(_selectedTimeForAlarm);

        return ti;
    }

    public static ArrayList<TileItem> MakeMany (ArrayList<String> _titles, ArrayList<String> _bodies) {
        final int _titlesSize = _titles.size();
        final int _bodiesSize = _bodies.size();

        if ( _titlesSize != _bodiesSize ) {
            throw new IllegalArgumentException("Both lists must have the same size");
        }

        ArrayList<TileItem> tileItems = new ArrayList<>();
        for (int i = 0; i < _titlesSize; i++) {
            TileItem ti = new TileItem(_titles.get(i), _bodies.get(i));
            tileItems.add(ti);
        }

        return tileItems;
    }

}
