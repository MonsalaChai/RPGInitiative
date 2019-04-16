package com.monsalachai.rpginitiative.persist.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "characters")
public class Character extends Monster {
    // Inherits columns from Monster

    // What gets added to Monster:
    // campaign id (which campaign the character belongs to)
    // 'is_active': indicates if the character is active in its campaign's fight.

    @ColumnInfo(name="campaign_id")
    protected long campaignUid;

    @ColumnInfo(name="is_active")
    protected boolean isActive;

    public long getCampaignUid() {
        return campaignUid;
    }

    public void setCampaignUid(long campaignUid) {
        this.campaignUid = campaignUid;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
