package com.monsalachai.rpginitiative.persist.room.entities;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("encounterId")},
        foreignKeys = {
            @ForeignKey(entity = EncounterData.class, parentColumns = "id", childColumns = "encounterId")
    })
public class CampaignData {
    @PrimaryKey(autoGenerate = true)
    protected long id;
    protected String name;
    protected String dmName;
    protected long createdOnDate;
    protected long encounterId;

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

    public long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(long encounterId) {
        this.encounterId = encounterId;
    }


}
