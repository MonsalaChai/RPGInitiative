package com.monsalachai.rpginitiative.persist.room.entities;

import androidx.room.Entity;

/**
 * This entity represents a join table between CampaignData and CharacterData
 *
 * The relationship is many-to-many.
 */
@Entity(primaryKeys = {"campaignId", "characterId"})
public class CampaignMemberData {
    public long campaignId;
    public long characterId;
}
