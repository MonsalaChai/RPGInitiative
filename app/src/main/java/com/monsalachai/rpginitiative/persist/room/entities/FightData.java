package com.monsalachai.rpginitiative.persist.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class FightData {
    @PrimaryKey(autoGenerate = true)
    protected long uid;

    @ColumnInfo(name="redirect_id")
    protected long characterId;

    @ColumnInfo(name="is_monster")
    protected boolean isMonster;

    @ColumnInfo(name="in_campaign")
    protected long campaignUid;

    @ColumnInfo(name="initiative")
    protected int initiative;

    @ColumnInfo(name="is_holding")
    protected boolean holdingAction;

    @ColumnInfo(name="is_selected")
    protected boolean isSelected;

    public FightData() {

    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(long characterId) {
        this.characterId = characterId;
    }

    public boolean isMonster() {
        return isMonster;
    }

    public void setMonster(boolean monster) {
        isMonster = monster;
    }

    public long getCampaignUid() {
        return campaignUid;
    }

    public void setCampaignUid(long campaignUid) {
        this.campaignUid = campaignUid;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public boolean isHoldingAction() {
        return holdingAction;
    }

    public void setHoldingAction(boolean holdingAction) {
        this.holdingAction = holdingAction;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
