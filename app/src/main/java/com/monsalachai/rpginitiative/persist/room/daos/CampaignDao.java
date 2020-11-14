package com.monsalachai.rpginitiative.persist.room.daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.monsalachai.rpginitiative.persist.room.Models;

import java.util.List;

@Dao
public abstract class CampaignDao {

    @Transaction
    @Query("SELECT * FROM campaigndata")
    public abstract List<Models.Campaign> getAllCampaigns();
}
