package com.monsalachai.rpginitiative.ui.bucket;

import com.monsalachai.rpginitiative.model.CharacterItem;

public interface OnBucketListActionListener {
    // Called when an item needs to be added to the fight.
    void onAddItemToFight(CharacterItem item, boolean isMonster);
    void onDeleteItemFromBench(CharacterItem item);
}
