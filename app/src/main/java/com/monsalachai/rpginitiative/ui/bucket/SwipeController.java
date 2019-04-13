package com.monsalachai.rpginitiative.ui.bucket;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.monsalachai.rpginitiative.persist.Persist;

import static android.support.v7.widget.helper.ItemTouchHelper.*; // for LEFT, RIGHT.

public class SwipeController extends ItemTouchHelper.Callback {
    protected static final String LTAG = "BLSC";

    protected RecyclerView mRecycler;
    protected boolean mRemoveOnSwipe;

    // This is a bit unideal, but one of the callbacks (onSwiped) needs access to
    // the recycler view (and its adapter)
    public SwipeController(RecyclerView recycler) {
        super();
        mRecycler = recycler;
        mRemoveOnSwipe = true;
    }

    public SwipeController(RecyclerView recycler, boolean removeOnSwipe) {
        super();
        mRecycler = recycler;
        mRemoveOnSwipe = removeOnSwipe;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        BucketRecyclerAdapter.ViewHolder vh = (BucketRecyclerAdapter.ViewHolder) viewHolder;
        Log.d(LTAG, "View Holder containing '" + vh.mCharacterItem.mCharacterName + "' got swiped: " + ((direction == 8) ? "RIGHT" : "LEFT"));

        // on swipe (right): add item to Active area (via persist)
        vh.mCharacterItem.mInitiative = 0;   // set to 0, just in case.
        Persist.markActive(vh.mCharacterItem);
        // and remove from adapter if necessary.
        if (mRemoveOnSwipe) {
            getRecyclerAdapter().mItems.remove(vh.mCharacterItem);
            getRecyclerAdapter().notifyDataSetChanged();
        }

    }

    protected BucketRecyclerAdapter getRecyclerAdapter() {
        return (BucketRecyclerAdapter) mRecycler.getAdapter();
    }
}
