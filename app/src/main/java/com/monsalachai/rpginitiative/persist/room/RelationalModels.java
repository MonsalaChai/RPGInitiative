package com.monsalachai.rpginitiative.persist.room;

import com.monsalachai.rpginitiative.persist.room.entities.CampaignData;
import com.monsalachai.rpginitiative.persist.room.entities.CampaignCharacterCrossRef;
import com.monsalachai.rpginitiative.persist.room.entities.CharacterData;
import com.monsalachai.rpginitiative.persist.room.entities.EncounterCombatantData;
import com.monsalachai.rpginitiative.persist.room.entities.EncounterData;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import androidx.room.Junction;

/**
 * Where the entity classes define what a schema within the database looks like, they don't excel
 * at defining the relationships they have amongst one another in place. To do that, new classes
 * must be made using the appropriate annotations to describe what entity data is being composed
 * as part of a relation. As these classes tend to be short and sweet, they're all defined here.
 */
public class RelationalModels {

    // Associates CampaignData to EncounterData, which is a one-to-one relationship.
    public static class Campaign {
        @Embedded
        public CampaignData campaign;

        // TODO, look into how to use relational model Encounter here instead of entity EncounterData
        @Relation(
                parentColumn = "encounterId",
                entityColumn = "id"
        )
        public EncounterData encounter;

        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = @Junction(
                        value = CampaignCharacterCrossRef.class,
                        parentColumn = "campaignId",
                        entityColumn = "characterId"
                )
        )
        public List<CharacterData> members;
    }

    // Associates an Encounter instance to its active and inactive combatants.
    public static class Encounter {
        @Embedded
        public EncounterData encounterData;

        @Relation(
                parentColumn = "characterOnDeck",
                entityColumn = "id"
        )
        public CharacterData activeCharacter;

        @Relation(parentColumn = "id",
                  entityColumn = "id",
                  associateBy = @Junction(
                          value = EncounterCombatantData.class,
                          parentColumn = "encounterId",
                          entityColumn = "characterId"
                  ))
        public List<CharacterData> allMembers;
    }

    public static class CampaignMember {
        @Embedded
        public CampaignCharacterCrossRef memberData;

        @Relation(
                parentColumn = "campaignId",
                entityColumn = "id"
        )
        public Campaign campaign;

        @Relation(
                parentColumn = "characterId",
                entityColumn = "id"
        )
        public CharacterData member;
    }
}
