package com.monsalachai.rpginitiative.ui.cif;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.monsalachai.rpginitiative.R;
import com.monsalachai.rpginitiative.model.CharacterItem;

import java.util.Collections;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CharacterItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CharacterInfoRecyclerViewAdapter extends RecyclerView.Adapter<CharacterInfoRecyclerViewAdapter.ViewHolder> {
    protected static final String LTAG = "CIRVA";
    private List<CharacterItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public CharacterInfoRecyclerViewAdapter(List<CharacterItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void setItems(List<CharacterItem> items) {
        Log.d(LTAG, "setItems items, old " + mValues.size() + " new " + items.size());
        mValues = items;

        update();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_characterinfo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mCharacterNameView.setText(mValues.get(position).mCharacterName);
        holder.mInitiativeView.setText(String.valueOf(mValues.get(position).mInitiative));

        //holder.mCharacterNameView.setTextColor(mValues.get(position).mHoldingTurn ? Color.RED : Color.BLACK);
        if (holder.mItem.mHoldingTurn)
            holder.mView.setBackgroundResource(R.color.color_fight_action_held);

        else
            // TODO: use default theme color rather than Color.WHITE.
            holder.mView.setBackgroundColor(Color.WHITE);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentShortTouch(holder.mItem);
                }
            }
        });

        holder.mInitiativeView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    int initiative;
                    try {
                        String text = v.getText().toString();
                        initiative = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        initiative = 0;
                    }

                    holder.mItem.mInitiative = initiative;
                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void update()
    {
        Collections.sort(mValues);
        notifyDataSetChanged();
    }

    public void removeItem(CharacterItem item) {
        mValues.remove(item);
        update();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final EditText mInitiativeView;
        public final TextView mCharacterNameView;
        public CharacterItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mInitiativeView = (EditText) view.findViewById(R.id.initiative_value);
            mCharacterNameView = (TextView) view.findViewById(R.id.character_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCharacterNameView.getText() + "'";
        }
    }
}
