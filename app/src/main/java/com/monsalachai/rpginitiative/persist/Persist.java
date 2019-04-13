package com.monsalachai.rpginitiative.persist;

import com.monsalachai.rpginitiative.model.CharacterItem;

import java.util.ArrayList;
import java.util.List;

public class Persist {
    public static List<CharacterItem> getAllCharacters(String campaign_name) {
        ArrayList<CharacterItem> items = new ArrayList<>();

        items.add(new CharacterItem("Balinope", 12));
        items.add(new CharacterItem("Broot", 3));
        items.add(new CharacterItem("Zinfandel", 5));
        items.add(new CharacterItem("Ezreal", 4));
        items.add(new CharacterItem("JimmyJohn", 17));
        items.add(new CharacterItem("Shoeshine", 8));
        items.add(new CharacterItem("Snek", 2));

        return items;
    }

    public static List<CharacterItem> getAllMonsters(){
        ArrayList<CharacterItem> items = new ArrayList<>();

        items.add(new CharacterItem("Bugbear", 0));
        items.add(new CharacterItem("Bandit", 0));
        items.add(new CharacterItem("Kobold", 0));

        return items;
    }

    public static List<CharacterItem> getActiveMonsters(String campaign_name) {
        ArrayList<CharacterItem> items = new ArrayList<>();

        for (int x = 0; x < 5; x++) {
            items.add(new CharacterItem("Kobold", (x*2)+3));
        }

        return items;
    }
}