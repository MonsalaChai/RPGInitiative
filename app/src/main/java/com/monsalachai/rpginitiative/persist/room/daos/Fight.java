package com.monsalachai.rpginitiative.persist.room.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.monsalachai.rpginitiative.persist.room.entities.FightData;

import java.util.List;

@Dao
public interface Fight {
    @Insert
    long insert(FightData fightData);

    @Delete
    void delete(FightData fightData);

    @Query("SELECT * FROM fightdata WHERE in_campaign IS :cuid")
    List<FightData> getByCampaignId(long cuid);
}
