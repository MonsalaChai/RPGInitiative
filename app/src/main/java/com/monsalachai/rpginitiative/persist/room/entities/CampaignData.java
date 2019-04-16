package com.monsalachai.rpginitiative.persist.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CampaignData {
    @PrimaryKey(autoGenerate = true)
    protected long uid;

    @ColumnInfo(name="name")
    protected String name;

    @ColumnInfo(name="dm_name")
    protected String dmName;

    @ColumnInfo(name="created_on")
    protected long createdOnDate;

    @ColumnInfo(name="num_fights")
    protected long numberOfFights;

    public CampaignData() {
        
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDmName() {
        return dmName;
    }

    public void setDmName(String dmName) {
        this.dmName = dmName;
    }

    public long getCreatedOnDate() {
        return createdOnDate;
    }

    public void setCreatedOnDate(long createdOnDate) {
        this.createdOnDate = createdOnDate;
    }

    public long getNumberOfFights() {
        return numberOfFights;
    }

    public void setNumberOfFights(long numberOfFights) {
        this.numberOfFights = numberOfFights;
    }
}
