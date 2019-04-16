package com.monsalachai.rpginitiative.persist.room.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.monsalachai.rpginitiative.persist.room.entities.CampaignData;

import java.util.List;

@Dao
public interface Campaign {
    @Insert
    long insert(CampaignData campaignData);

    @Delete
    void delete(CampaignData campaignData);

    @Query("SELECT * FROM campaigndata")
    List<CampaignData> getAll();

    @Query("SELECT * FROM campaigndata WHERE uid IS :uid LIMIT 1")
    CampaignData getByUid(long uid);

    @Query("SELECT * FROM campaigndata WHERE name IS :name LIMIT 1")
    CampaignData getByName(String name);
}
