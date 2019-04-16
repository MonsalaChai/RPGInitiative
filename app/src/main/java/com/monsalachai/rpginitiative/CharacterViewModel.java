package com.monsalachai.rpginitiative;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.monsalachai.rpginitiative.model.CharacterItem;
import com.monsalachai.rpginitiative.persist.Persist;

import java.util.ArrayList;
import java.util.List;

public class CharacterViewModel extends ViewModel {
    protected static final String LTAG = "CVM";
    private final MutableLiveData<List<CharacterItem>> mBenchTeam = new MutableLiveData<>();
    private final MutableLiveData<List<CharacterItem>> mFightTeam = new MutableLiveData<>();

    public void initBenchTeam(List<CharacterItem> items) {
        mBenchTeam.setValue(items);
    }

    public void initFightTeam(List<CharacterItem> items) {
        mFightTeam.setValue(items);
    }

    public void moveToFightTeam(CharacterItem item) {
        Log.d(LTAG, "moveToFightTeam");
        Persist.addToFight("demo", item);
        moveCharacter(item, true);
    }

    public void moveToBenchTeam(CharacterItem item) {
        Log.d(LTAG, "moveToBenchTeam");

        // Inform persist of the change.
        Persist.removeFromFight("demo", item);

        if (item.isMonster()) {
            // just remove from fight team.
            List<CharacterItem> fightItems = mFightTeam.getValue();
            fightItems.remove(item);
            mFightTeam.setValue(fightItems);
        }
        else {
            moveCharacter(item, false);
        }

    }

    private void moveCharacter(CharacterItem item, boolean isToFight) {
        List<CharacterItem> fightTeam = mFightTeam.getValue();
        if (fightTeam == null) {
            fightTeam = new ArrayList<>();
        }

        List<CharacterItem> benchTeam = mBenchTeam.getValue();
        if (benchTeam == null) {
            benchTeam = new ArrayList<>();
        }

        Log.d(LTAG, "moving " + item + " " + (isToFight ? "to fight" : "to bench"));
        Log.d(LTAG, "moveCharacter fightTeam size " + fightTeam.size() + " bench team size " + benchTeam.size());

        List<CharacterItem> to;
        List<CharacterItem> from;

        if (isToFight) {
            to = fightTeam;
            from = benchTeam;
        } else {
            to = benchTeam;
            from = fightTeam;
        }

        to.add(item);
        from.remove(item);

        mFightTeam.setValue(fightTeam);
        mBenchTeam.setValue(benchTeam);
    }

    public MutableLiveData<List<CharacterItem>> getFightTeam() {
        return mFightTeam;
    }

    public MutableLiveData<List<CharacterItem>> getBenchTeam() {
        return mBenchTeam;
    }

    public void addMonsterToFight(CharacterItem monster) {
        List<CharacterItem> fightItems = mFightTeam.getValue();
        fightItems.add(monster);
        mFightTeam.setValue(fightItems);
    }

    public void addNewCharacterToBench(CharacterItem item) {
        List<CharacterItem> benchItems = mBenchTeam.getValue();
        benchItems.add(item);
        mBenchTeam.setValue(benchItems);
    }
}
