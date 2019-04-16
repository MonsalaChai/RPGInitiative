package com.monsalachai.rpginitiative.persist.room.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface Character {
    @Insert
    long insert(com.monsalachai.rpginitiative.persist.room.entities.Character character);

    @Delete
    void delete(com.monsalachai.rpginitiative.persist.room.entities.Character character);

    @Query("SELECT * FROM characters WHERE campaign_id IS :cuid")
    List<com.monsalachai.rpginitiative.persist.room.entities.Character> getByCampaign(long cuid);

}
