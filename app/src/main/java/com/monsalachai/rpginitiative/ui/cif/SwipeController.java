package com.monsalachai.rpginitiative.ui.cif;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import static android.support.v7.widget.helper.ItemTouchHelper.*;

public class SwipeController extends ItemTouchHelper.Callback {
    protected OnListFragmentInteractionListener mListener;

    public SwipeController(OnListFragmentInteractionListener callback) {
        mListener = callback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT); // Allows the user to drag ViewHolders to the right.
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // Send the character item in question on back to the bucket list (if its a character)
        // or just remove it if its a monster.
        mListener.onListFragmentSwipe(((CharacterInfoRecyclerViewAdapter.ViewHolder) viewHolder).mItem);
    }
}
