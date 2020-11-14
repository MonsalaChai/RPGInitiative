package com.monsalachai.rpginitiative.persist.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EncounterData {
    @PrimaryKey(autoGenerate = true)
    protected long id;
    protected String description;
    protected long characterOnDeck;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCharacterOnDeck() {
        return characterOnDeck;
    }

    public void setCharacterOnDeck(long characterOnDeck) {
        this.characterOnDeck = characterOnDeck;
    }
}
