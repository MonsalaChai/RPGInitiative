package com.monsalachai.rpginitiative.persist;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.monsalachai.rpginitiative.model.CharacterItem;
import com.monsalachai.rpginitiative.persist.room.Database;
import com.monsalachai.rpginitiative.persist.room.daos.Fight;
import com.monsalachai.rpginitiative.persist.room.entities.CampaignData;
import com.monsalachai.rpginitiative.persist.room.entities.Character;
import com.monsalachai.rpginitiative.persist.room.entities.FightData;
import com.monsalachai.rpginitiative.persist.room.entities.Monster;

import java.util.ArrayList;
import java.util.LinkedList;
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

    public static void add(CharacterItem item) {
        if (mDatabase == null) return;
        if (!item.isMonster()) return;

        Monster m = new Monster();
        m.setName(item.mCharacterName);
        m.setUid(mDatabase.monsterDao().insert(m));
        item.setPersistId(m.getUid());
    }

    public static void add(String campaignName, CharacterItem item) {
        if (mDatabase == null) return;
        if (item.isMonster()) {
            add(item);
            return;
        }

        Character c = new Character();
        c.setName(item.mCharacterName);
        c.setCampaignUid(getCampaignByName(campaignName).getUid());
        c.setUid(mDatabase.characterDao().insert(c));
        item.setPersistId(c.getUid());
    }

    public static void removeFromFight(String campaignName, CharacterItem item) {
        if (mDatabase == null) return;

        // Find the fight data object.
        Fight fightDao = mDatabase.fightDao();

        FightData row = fightDao.findObject(getCampaignByName(campaignName).getUid(), item.getFightId());
        if (row != null) {
            // remove from fight data table.
            fightDao.delete(row);
            item.setFightId(0);
        }
    }

    public static List<CharacterItem> loadAllCombatants(String campaignName) {
        // Load a character item for every entry in FightData that matches specified campaign.
        if (mDatabase == null) return null;

        CampaignData campaignData = getCampaignByName(campaignName);
        List<FightData> rows = mDatabase.fightDao().getByCampaignId(campaignData.getUid());
        LinkedList<CharacterItem> items = new LinkedList<>();

        for (FightData row : rows) {
            // Load from the appropriate character / monster table.
            CharacterItem item = new CharacterItem();
            item.setPersistId(row.getCharacterId());
            item.setFightId(row.getUid());
            item.mInitiative = row.getInitiative();

            Monster m = null;
            if (row.isMonster()) {
                item.setIsMonster(true);
                m = mDatabase.monsterDao().getById(item.getPersistId());
            }
            else {
                m = mDatabase.characterDao().getById(item.getPersistId());
            }
            item.mCharacterName = m.getName();

            items.add(item);
            Log.d(LTAG, "Loaded combatant: " + item);
        }

        return items;
    }

    public static List<CharacterItem> loadNonCombatants(String campaignName) {
        return loadNonCombatants(campaignName, loadAllCombatants(campaignName));
    }

    public static List<CharacterItem> loadNonCombatants(String campaignName, List<CharacterItem> combatantSet) {
        if (mDatabase == null) return null;
        // Create an id-only list.
        ArrayList<Long> idList = new ArrayList<>();
        for (CharacterItem item : combatantSet) idList.add(item.getPersistId());

        // Get all Character entities not represented by the set of IDs
        List<Character> entities = mDatabase.characterDao().getMissingByCampaign(getCampaignByName(campaignName).getUid(), idList);

        // Convert the Character entities to CharacterItems.
        LinkedList<CharacterItem> items = new LinkedList<>();
        for (Character c: entities) {
            CharacterItem item = new CharacterItem(c.getName(), 0);
            item.setPersistId(c.getUid());
            items.add(item);
            Log.d(LTAG, "Loaded noncombatant: " + item);
        }

        return items;
    }

    public static void addToFight(String campaignName, CharacterItem item) {
        if (mDatabase == null) return;
        if (item.getFightId() != 0) return; // already in fight.
        long cuid = getCampaignByName(campaignName).getUid();

        FightData fd = new FightData();
        fd.setCampaignUid(cuid);
        fd.setCharacterId(item.getPersistId());
        fd.setHoldingAction(item.mHoldingTurn);
        fd.setInitiative(item.mInitiative);
        fd.setMonster(item.isMonster());
        fd.setUid(mDatabase.fightDao().insert(fd));
        item.setFightId(fd.getUid());
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
            CharacterItem characterItem = new CharacterItem(monster.getName(), 0);
            characterItem.setIsMonster(true);
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

    public static boolean confirmUnique(String campaign, String name, boolean isMonster) {
        if (mDatabase == null) return false;
        if (isMonster) {
            Monster m = mDatabase.monsterDao().getByName(name);
            return (m == null);
        }
        else {
            long cuid = getCampaignByName(campaign).getUid();
            Character c = mDatabase.characterDao().getByName(cuid, name);
            return (c == null);
        }
    }

    private static CampaignData getCampaignByName(String name) {
        if (mDatabase == null) return null;
        CampaignData cd = mDatabase.campaignDao().getByName(name);
        if (cd == null) {
            cd = new CampaignData();
            cd.setName(name);
            cd.setUid(mDatabase.campaignDao().insert(cd));
        }
        return cd;
    }
}
