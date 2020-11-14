package com.monsalachai.rpginitiative.persist;

import androidx.room.Room;
import android.content.Context;
import com.monsalachai.rpginitiative.model.CharacterItem;
import com.monsalachai.rpginitiative.persist.room.Database;
import com.monsalachai.rpginitiative.persist.room.entities.CampaignData;
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
    }

    public static void add(String campaignName, CharacterItem item) {
        if (mDatabase == null) return;
        if (item.isMonster()) {
            add(item);
            return;
        }
    }

    public static void removeFromFight(String campaignName, CharacterItem item) {
        if (mDatabase == null) return;
    }

    public static List<CharacterItem> loadAllCombatants(String campaignName) {
        // Load a character item for every entry in FightData that matches specified campaign.
        if (mDatabase == null) return null;
        return null;
    }

    public static List<CharacterItem> loadNonCombatants(String campaignName) {
        return null; //return loadNonCombatants(campaignName, loadAllCombatants(campaignName));
    }

    public static List<CharacterItem> loadNonCombatants(String campaignName, List<CharacterItem> combatantSet) {
        if (mDatabase == null) return null;
        // Create an id-only list.
        return null;
        /*
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
        */
    }

    public static void addToFight(String campaignName, CharacterItem item) {
        if (mDatabase == null) return;
        if (item.getFightId() != 0) return; // already in fight.

        /*
        long cuid = getCampaignByName(campaignName).getUid();

        FightData fd = new FightData();
        fd.setCampaignUid(cuid);
        fd.setCharacterId(item.getPersistId());
        fd.setHoldingAction(item.mHoldingTurn);
        fd.setInitiative(item.mInitiative);
        fd.setMonster(item.isMonster());
        fd.setUid(mDatabase.fightDao().insert(fd));
        item.setFightId(fd.getUid());
        */
    }

    public static List<CharacterItem> getAllCharacters(String campaignName) {
        // find campaign id by name.
        /*
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
        */
        return null;
    }

    public static List<CharacterItem> getAllMonsters(){
        /*
        List<Monster> monsters = mDatabase.monsterDao().getAllMonsters();
        ArrayList<CharacterItem> items = new ArrayList<>();
        for (Monster monster : monsters) {
            CharacterItem characterItem = new CharacterItem(monster.getName(), 0);
            characterItem.setIsMonster(true);
            items.add(new CharacterItem(monster.getName(), 0));
        }
        return items;
        */
        return null;
    }

    public static List<CharacterItem> getActiveMonsters(String campaign_name) {
        // load the fight table, filter entries by campaign
        // look for non-character rows.
        return null;
        /*
        ArrayList<CharacterItem> items = new ArrayList<>();

        for (int x = 0; x < 5; x++) {
            items.add(new CharacterItem("Kobold", (x*2)+3));
        }

        return items;
        */
    }

    public static void populate(String campaignName) {
        return;
        /*
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
        */
    }

    public static void clear(String campaignName) {
        /*
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
        */
    }

    public static void markActive(CharacterItem item) {
    }

    public static void markInactive(CharacterItem item) {
        // called by the character info fragment when the user has kicked out
        // an item.
    }

    public static boolean confirmUnique(String campaign, String name, boolean isMonster) {
        /*if (mDatabase == null) return false;
        if (isMonster) {
            Monster m = mDatabase.monsterDao().getByName(name);
            return (m == null);
        }
        else {
            long cuid = getCampaignByName(campaign).getUid();
            Character c = mDatabase.characterDao().getByName(cuid, name);
            return (c == null);
        }
        */
        return false;
    }

    private static CampaignData getCampaignByName(String name) {
        /*
        if (mDatabase == null) return null;
        CampaignData cd = mDatabase.campaignDao().getByName(name);
        if (cd == null) {
            cd = new CampaignData();
            cd.setName(name);
            cd.setUid(mDatabase.campaignDao().insert(cd));
        }
        return cd;
        */
        return null;
    }
}
