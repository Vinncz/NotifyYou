package com.example.notifyyou.Models;

public class TileItem {

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

    public String getTitle () {
        return this.title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

}
