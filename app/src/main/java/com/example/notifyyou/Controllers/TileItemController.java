package com.example.notifyyou.Controllers;

public class TileItemController {

    public Boolean validate (String _title, String _body) {
        return checkTitle(_title) && checkBody(_body);
    }

    private Boolean checkTitle (String _t) {
        boolean flag = true;

        /* _t is not null and is not empty */
        flag = _t != null && !_t.isEmpty() && !_t.isBlank() && _t.trim().length() >= 3;

        return flag;
    }

    private Boolean checkBody (String _b) {
        boolean flag = true;

        flag = _b != null && !_b.isEmpty() && !_b.isBlank() && _b.trim().length() >= 3;

        return flag;
    }

}
