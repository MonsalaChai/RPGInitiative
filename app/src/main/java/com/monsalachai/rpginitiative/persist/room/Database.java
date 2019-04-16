package com.monsalachai.rpginitiative.persist.room;

import android.arch.persistence.room.RoomDatabase;

import com.monsalachai.rpginitiative.persist.room.daos.Campaign;
import com.monsalachai.rpginitiative.persist.room.daos.Fight;
import com.monsalachai.rpginitiative.persist.room.entities.CampaignData;
import com.monsalachai.rpginitiative.persist.room.entities.FightData;
import com.monsalachai.rpginitiative.persist.room.entities.Monster;

@android.arch.persistence.room.Database(entities = {CampaignData.class, Monster.class,
        com.monsalachai.rpginitiative.persist.room.entities.Character.class,
        FightData.class},
        version=1)
public abstract class Database extends RoomDatabase {
    public abstract Campaign campaignDao();
    public abstract com.monsalachai.rpginitiative.persist.room.daos.Monster monsterDao();
    public abstract com.monsalachai.rpginitiative.persist.room.daos.Character characterDao();
    public abstract Fight fightDao();
}
