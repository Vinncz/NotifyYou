package com.example.notifyyou.Models;

public class TileItem {
    private String Name;
    private String Desc;

    public TileItem (String name, String desc) {
        Name = name;
        Desc = desc;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
