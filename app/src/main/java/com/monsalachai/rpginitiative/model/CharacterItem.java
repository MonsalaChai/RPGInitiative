package com.monsalachai.rpginitiative.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class CharacterItem implements Serializable, Comparable<CharacterItem> {
    public String mCharacterName;
    public int mInitiative;
    public boolean mHoldingTurn;
    public boolean mAlive;

    public CharacterItem(String name, int initiative)
    {
        mCharacterName = name;
        mInitiative = initiative;
        mHoldingTurn = false;
        mAlive = true;
    }

    @Override
    public int compareTo(@NonNull CharacterItem o) {
        // Higher initiative is better, so sort it earlier.
        return o.mInitiative - this.mInitiative;
    }

    @Override
    public String toString() {
        return "{" +
                "'" + mCharacterName + '\'' +
                ", init=" + mInitiative +
                ", hold=" + mHoldingTurn +
                ", alive=" + mAlive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterItem that = (CharacterItem) o;
        return mInitiative == that.mInitiative &&
                mHoldingTurn == that.mHoldingTurn &&
                mAlive == that.mAlive &&
                Objects.equals(mCharacterName, that.mCharacterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mCharacterName, mInitiative, mHoldingTurn, mAlive);
    }
}
