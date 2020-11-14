package com.monsalachai.rpginitiative.persist.room;

import androidx.room.RoomDatabase;

import com.monsalachai.rpginitiative.persist.room.daos.CampaignDao;
import com.monsalachai.rpginitiative.persist.room.entities.CampaignData;
import com.monsalachai.rpginitiative.persist.room.entities.CampaignCharacterCrossRef;
import com.monsalachai.rpginitiative.persist.room.entities.CharacterData;
import com.monsalachai.rpginitiative.persist.room.entities.EncounterCombatantData;
import com.monsalachai.rpginitiative.persist.room.entities.EncounterData;


@androidx.room.Database(entities = {CampaignData.class, CampaignCharacterCrossRef.class,
            CharacterData.class, EncounterCombatantData.class, EncounterData.class},
        version=2)
public abstract class Database extends RoomDatabase {
    public abstract CampaignDao campaignDao();
}
