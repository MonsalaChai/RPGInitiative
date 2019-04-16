package com.monsalachai.rpginitiative.persist.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName="monsters")
public class Monster {
    @PrimaryKey(autoGenerate = true)
    protected long uid;

    @ColumnInfo(name="name")
    protected String name;

    @ColumnInfo(name="base_init")
    protected int initMod;

    public Monster() {

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

    public int getInitMod() {
        return initMod;
    }

    public void setInitMod(int initMod) {
        this.initMod = initMod;
    }
}
