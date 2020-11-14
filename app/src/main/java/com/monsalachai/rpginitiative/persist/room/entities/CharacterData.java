package com.monsalachai.rpginitiative.persist.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CharacterData {
    @PrimaryKey(autoGenerate = true)
    protected long id;
    protected String name;
    protected int baseInitiative;
    protected boolean isReplicable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseInitiative() {
        return baseInitiative;
    }

    public void setBaseInitiative(int baseInitiative) {
        this.baseInitiative = baseInitiative;
    }

    public boolean isReplicable() {
        return isReplicable;
    }

    public void setReplicable(boolean replicable) {
        isReplicable = replicable;
    }
}
