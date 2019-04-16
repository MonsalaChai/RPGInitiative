package com.monsalachai.rpginitiative.persist;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.monsalachai.rpginitiative.model.CharacterItem;
import com.monsalachai.rpginitiative.persist.room.Database;
import com.monsalachai.rpginitiative.persist.room.entities.CampaignData;
import com.monsalachai.rpginitiative.persist.room.entities.Character;
import com.monsalachai.rpginitiative.persist.room.entities.Monster;

import java.util.ArrayList;
import java.util.List;

public final class Persist {
    protected static final String LTAG = "PERSIST";
    protected static Database mDatabase;

    public static void init(Context context) {
        if (mDatabase == null) {
            mDatabase = Room.databaseBuilder(context, Database.class, "main_db")
                    .allowMainThreadQueries()
                    .build();
        }
    }

    public static List<CharacterItem> getAllCharacters(String campaignName) {
        // find campaign id by name.
        CampaignData cd = mDatabase.campaignDao().getByName(campaignName);
        if (cd == null) {
            cd = new CampaignData();
            cd.setName(campaignName);
            cd.setCreatedOnDate(System.currentTimeMillis()/1000l);
            cd.setUid((mDatabase.campaignDao().insert(cd)));
        }

        // now find all characters in that campaign.
        List<Character> characters = mDatabase.characterDao().getByCampaign(cd.getUid());

        // now convert all to character items.
        ArrayList<CharacterItem> chitems = new ArrayList<>();
        for (Character ch : characters) {
            chitems.add(new CharacterItem(ch.getName(), 0));    // todo: pull characters marked as active and apply their init here.
        }

        return chitems;
    }

    public static List<CharacterItem> getAllMonsters(){
        List<Monster> monsters = mDatabase.monsterDao().getAllMonsters();
        ArrayList<CharacterItem> items = new ArrayList<>();
        for (Monster monster : monsters) {
            items.add(new CharacterItem(monster.getName(), 0));
        }
        return items;

    }

    public static List<CharacterItem> getActiveMonsters(String campaign_name) {
        // load the fight table, filter entries by campaign
        // look for non-character rows.
        ArrayList<CharacterItem> items = new ArrayList<>();

        for (int x = 0; x < 5; x++) {
            items.add(new CharacterItem("Kobold", (x*2)+3));
        }

        return items;
    }

    public static void populate(String campaignName) {
        CampaignData cd = mDatabase.campaignDao().getByName(campaignName);
        if (cd == null) {
            cd = new CampaignData();
            cd.setName(campaignName);
            cd.setCreatedOnDate(System.currentTimeMillis()/1000l);
            cd.setUid((mDatabase.campaignDao().insert(cd)));
        }

        if (mDatabase.characterDao().getByCampaign(cd.getUid()).size() == 0) {
            ArrayList<CharacterItem> items = new ArrayList<>();

            items.add(new CharacterItem("Balinope", 12));
            items.add(new CharacterItem("Broot", 3));
            items.add(new CharacterItem("Zinfandel", 5));
            items.add(new CharacterItem("Ezreal", 4));
            items.add(new CharacterItem("JimmyJohn", 17));
            items.add(new CharacterItem("Shoeshine", 8));
            items.add(new CharacterItem("Snek", 2));
            items.add(new CharacterItem("Mugark", 2));

            for (CharacterItem item : items) {
                Character ch = new Character();
                ch.setName(item.mCharacterName);
                ch.setCampaignUid(cd.getUid());
                mDatabase.characterDao().insert(ch);
                // set stuff in fight table t oo.
                Log.d(LTAG, "Adding item to character table. (" + ch.getName() + ")");
            }

            items = new ArrayList<>();

            items.add(new CharacterItem("Bugbear", 0));
            items.add(new CharacterItem("Bandit", 0));
            items.add(new CharacterItem("Kobold", 0));

            for (CharacterItem item : items) {
                Monster monster = new Monster();
                monster.setName(item.mCharacterName);
                mDatabase.monsterDao().insert(monster);
            }
        }
        else {
            Log.d(LTAG, "Table not empty.");
        }
    }

    public static void clear(String campaignName) {
        CampaignData cd = mDatabase.campaignDao().getByName(campaignName);
        List<Character> characters = mDatabase.characterDao().getByCampaign(cd.getUid());

        for (Character ch : characters) {
            mDatabase.characterDao().delete(ch);
        }
        Log.d(LTAG, "Deleted: " + characters.size() + " items from character table");


        List<Monster> monsters = mDatabase.monsterDao().getAllMonsters();
        for (Monster monster: monsters) {
            mDatabase.monsterDao().delete(monster);
        }
        Log.d(LTAG, "Deleted " + monsters.size() + " monster from the monster table");
    }

    public static void markActive(CharacterItem item) {
        // Called by bucket list view when an item is being submitted
        // to the active table.
        Log.d(LTAG, "Marking item: " + item.mCharacterName + " as active, with initiative: " + item.mInitiative);
    }

    public static void markInactive(CharacterItem item) {
        // called by the character info fragment when the user has kicked out
        // an item.
    }
}
