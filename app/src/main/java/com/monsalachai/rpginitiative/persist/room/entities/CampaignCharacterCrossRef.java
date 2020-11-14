package com.monsalachai.rpginitiative.persist.room.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

/**
 * This entity represents a join table between CampaignData and CharacterData
 *
 * The relationship is many-to-many.
 */
@Entity(primaryKeys = {"campaignId", "characterId"},
        indices = {@Index("campaignId"), @Index("characterId")},
        foreignKeys = {
            @ForeignKey(entity = CampaignData.class, parentColumns = "id", childColumns = "campaignId"),
            @ForeignKey(entity = CharacterData.class, parentColumns = "id", childColumns = "characterId")
    })
public class CampaignCharacterCrossRef {
    public long campaignId;
    public long characterId;
}
