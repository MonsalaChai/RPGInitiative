package com.monsalachai.rpginitiative.persist.room.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface Monster {
    @Insert
    long insert(com.monsalachai.rpginitiative.persist.room.entities.Monster monster);

    @Delete
    void delete(com.monsalachai.rpginitiative.persist.room.entities.Monster monster);

    @Query("SELECT * FROM monsters")
    List<com.monsalachai.rpginitiative.persist.room.entities.Monster> getAllMonsters();

    @Query("SELECT * FROM monsters WHERE name LIKE :name LIMIT 1")
    com.monsalachai.rpginitiative.persist.room.entities.Monster getByName(String name);


}
