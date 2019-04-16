package com.monsalachai.rpginitiative.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class CharacterItem implements Serializable, Comparable<CharacterItem> {
    public String mCharacterName;
    public int mInitiative;
    public boolean mHoldingTurn;
    public boolean mAlive;
    protected boolean mIsMonster;
    protected long mPersistId;   // the item's UID in its repsective table (monster or character)
    protected long mFightId;     // the item's UID in the fight table, if it is in the fight table.

    public CharacterItem() {
        mCharacterName = "";
        mInitiative = 0;
        mHoldingTurn = false;
        mAlive = true;
        mIsMonster = false;
        mFightId = 0;
        mPersistId = 0;
    }

    public CharacterItem(String name, int initiative)
    {
        mCharacterName = name;
        mInitiative = initiative;
        mHoldingTurn = false;
        mAlive = true;
        mIsMonster = false;
        mPersistId = 0;
        mFightId = 0;
    }

    public boolean isMonster() {
        return mIsMonster;
    }

    public void setIsMonster(boolean state) {
        mIsMonster = state;
    }

    public long getPersistId() {
        return mPersistId;
    }

    public void setPersistId(long persistId) {
        this.mPersistId = persistId;
    }

    public void setFightId(long fightId) {
        mFightId = fightId;
    }

    public long getFightId() {
        return mFightId;
    }

    @Override
    public int compareTo(@NonNull CharacterItem o) {
        // Higher initiative is better, so sort it earlier.
        int firstOrder = o.mInitiative - this.mInitiative;
        return (firstOrder != 0) ? firstOrder :  this.mCharacterName.compareTo(o.mCharacterName);
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
