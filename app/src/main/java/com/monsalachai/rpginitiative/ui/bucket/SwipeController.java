package com.monsalachai.rpginitiative.ui.bucket;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;


import com.monsalachai.rpginitiative.MainActivity;
import com.monsalachai.rpginitiative.persist.Persist;

import static android.support.v7.widget.helper.ItemTouchHelper.*; // for LEFT, RIGHT.

public class SwipeController extends ItemTouchHelper.Callback {
    protected static final String LTAG = "BLSC";

    protected RecyclerView mRecycler;
    protected BucketListView mBucketListView;
    protected boolean mRemoveOnSwipe;

    // This is a bit unideal, but one of the callbacks (onSwiped) needs access to
    // the recycler view (and its adapter)
    public SwipeController(BucketListView blv, RecyclerView recycler) {
        super();
        mRecycler = recycler;
        mRemoveOnSwipe = true;
        mBucketListView = blv;
    }

    public SwipeController(BucketListView blv, RecyclerView recycler, boolean removeOnSwipe) {
        super();
        mBucketListView = blv;
        mRecycler = recycler;
        mRemoveOnSwipe = removeOnSwipe;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, RIGHT); // Allows the user to drag ViewHolders to the right.
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        BucketRecyclerAdapter.ViewHolder vh = (BucketRecyclerAdapter.ViewHolder) viewHolder;

        // on swipe (right): add item to Active area (via persist)
        vh.mCharacterItem.mInitiative = 0;   // Reset initiative score.

        // Notify callback of data transition.
        if (mBucketListView.getActionListener() != null) {
            // The last parameter is a bit hacky, but the only reason to not remove on swipe is if
            // the item is a monster.
            mBucketListView.getActionListener().onAddItemToFight(vh.mCharacterItem, !mRemoveOnSwipe);
        }

        // and remove from adapter if necessary.
        if (mRemoveOnSwipe)
            getRecyclerAdapter().mItems.remove(vh.mCharacterItem);

        // Update the adapter, in the event an object is removed the cards are rearranged correctly
        // in the event it wasnt, then the card is restored to its default position.
        getRecyclerAdapter().notifyDataSetChanged();
    }


    protected BucketRecyclerAdapter getRecyclerAdapter() {
        return (BucketRecyclerAdapter) mRecycler.getAdapter();
    }
}
