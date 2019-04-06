package com.monsalachai.rpginitiative.model;

import java.io.Serializable;

public class CharacterItem implements Serializable {
    public String mCharacterName;
    public int mInitiative;
    public boolean mHoldingTurn;

    public CharacterItem(String name, int initiative)
    {
        mCharacterName = name;
        mInitiative = initiative;
        mHoldingTurn = false;
    }
}
