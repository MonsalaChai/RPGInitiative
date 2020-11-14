package com.monsalachai.rpginitiative.persist.room.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

/**
 * This entity represents a join table between EncounterData and CharacterData
 */
@Entity(primaryKeys = {"encounterId", "characterId"},
        foreignKeys = {
            @ForeignKey(entity = EncounterData.class, parentColumns = "id", childColumns = "encounterId"),
            @ForeignKey(entity = CharacterData.class, parentColumns = "id", childColumns = "characterId")
        })
public class EncounterCombatantData {
    protected long encounterId;
    protected long characterId;
    protected String nameExtension;
    protected int realInitiative;
    protected long statusEffects;

    public long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(long encounterId) {
        this.encounterId = encounterId;
    }

    public long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(long characterId) {
        this.characterId = characterId;
    }

    public String getNameExtension() {
        return nameExtension;
    }

    public void setNameExtension(String nameExtension) {
        this.nameExtension = nameExtension;
    }

    public int getRealInitiative() {
        return realInitiative;
    }

    public void setRealInitiative(int realInitiative) {
        this.realInitiative = realInitiative;
    }

    public long getStatusEffects() {
        return statusEffects;
    }

    public void setStatusEffects(long statusEffects) {
        this.statusEffects = statusEffects;
    }
}
