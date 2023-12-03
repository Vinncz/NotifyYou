package com.example.notifyyou.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.notifyyou.Utils.Converters.TimestampConverter;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity(tableName = "tile_items")
@TypeConverters({TimestampConverter.class})
public class TileItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String title;
    private String body;
    private Boolean isPinned;
    private Boolean alarmIsActive;
    private String selectedTimeForAlarm;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    /* CONSTRUCTOR */
    public TileItem (String _title, String _body) {
        this.title = _title;
        this.body  = _body;
        this.isPinned = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.modifiedAt = this.createdAt;
    }
    public TileItem (String _title, String _body, Boolean _isPinned) {
        this.title = _title;
        this.body  = _body;
        this.isPinned = _isPinned;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.modifiedAt = this.createdAt;
    }
    public TileItem () {}


    /* SETTERS */
    public void setId (Integer _id) {
        this.id = _id;
    }
    public void setTitle (String _title) {
        this.title = _title;
    }
    public void setBody (String _body) {
        this.body = _body;
    }
    public void setIsPinned (Boolean _pinned) { this.isPinned = _pinned; }
    public void setAlarmIsActive (Boolean _status) {
        this.alarmIsActive = _status;
    }
    public void setSelectedTimeForAlarm (String _selectedTimeForAlarm) { this.selectedTimeForAlarm = _selectedTimeForAlarm; }
    public void setModifiedAt (Timestamp _timestamp) { this.modifiedAt = _timestamp; }
    public void setCreatedAt (Timestamp _timestamp) { this.createdAt = _timestamp; }


    /* GETTERS */
    public Integer getId () { return id; }
    public String getTitle () { return title; }
    public String getBody () { return body; }
    public Boolean getIsPinned () { return isPinned; }
    public Boolean getAlarmIsActive () {
        return alarmIsActive;
    }
    public String getSelectedTimeForAlarm () { return selectedTimeForAlarm; }
    public Timestamp getCreatedAt () { return createdAt; }
    public Timestamp getModifiedAt () { return modifiedAt; }

}
