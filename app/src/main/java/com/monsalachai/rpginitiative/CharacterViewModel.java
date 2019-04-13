package com.monsalachai.rpginitiative;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.monsalachai.rpginitiative.model.CharacterItem;

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
        moveCharacter(item, true);
    }

    public void moveToBenchTeam(CharacterItem item) {
        Log.d(LTAG, "moveToBenchTeam");
        moveCharacter(item, false);
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
}
