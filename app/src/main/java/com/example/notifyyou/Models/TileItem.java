package com.example.notifyyou.Models;

public class TileItem {

    /*
    * TODO:
    *  1. Buat SharedPreference yang tugasnya untuk nge-track used-id untuk setiap object TileItem
    *  2. Ketika new TileItem dibuat, maka used-id harus di-increment
    */

    private Integer id;
    private String title;
    private String body;

    public TileItem (String _title, String _body) {
        this.title = _title;
        this.body = _body;
    }

    public String getBody () {
        return body;
    }

    public void setBody (String body) {
        this.body = body;
    }

    public Integer getId () {
        return this.id;
    }

    public String getTitle () {
        return this.title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

}
