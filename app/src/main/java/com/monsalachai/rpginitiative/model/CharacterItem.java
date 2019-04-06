package com.monsalachai.rpginitiative.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class CharacterItem implements Serializable, Comparable<CharacterItem> {
    public String mCharacterName;
    public int mInitiative;
    public boolean mHoldingTurn;

    public CharacterItem(String name, int initiative)
    {
        mCharacterName = name;
        mInitiative = initiative;
        mHoldingTurn = false;
    }

    @Override
    public int compareTo(@NonNull CharacterItem o) {
        // Higher initiative is better, so sort it earlier.
        return o.mInitiative - this.mInitiative;
    }

    @Override
    public String toString() {
        return "CharacterItem{" +
                "mCharacterName='" + mCharacterName + '\'' +
                ", mInitiative=" + mInitiative +
                ", mHoldingTurn=" + mHoldingTurn +
                '}';
    }
}
