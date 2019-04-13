package com.monsalachai.rpginitiative.ui.bucket;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.monsalachai.rpginitiative.R;
import com.monsalachai.rpginitiative.model.CharacterItem;
import com.monsalachai.rpginitiative.persist.Persist;

import java.util.ArrayList;
import java.util.List;

public class BucketRecyclerAdapter extends RecyclerView.Adapter<BucketRecyclerAdapter.ViewHolder> {
    final static String LTAG = "BRA";
    protected List<CharacterItem> mItems;
    protected boolean mRemoveOnSubmit;

    class ViewHolder extends RecyclerView.ViewHolder {
        final static String LTAG = "BRAVH";
        CharacterItem mCharacterItem;
        final View mView;
        final TextView mNameView;
        final CardView mCardView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mCardView = (CardView) view.findViewById(R.id.card);    // this should be mView, but
                                                                    // hopefully this will help
                                                                    // future proof the code
        }

        void setCharacterItem(CharacterItem item) {
            mCharacterItem = item;
            setNameText(item.mCharacterName);
        }

        void setNameText(String name) {
            mNameView.setText(name);
        }
    }

    public BucketRecyclerAdapter(List<CharacterItem> items) {
        mItems = new ArrayList<>(items);
        mRemoveOnSubmit = true;
    }

    public BucketRecyclerAdapter(List<CharacterItem> items, boolean removeOnSubmit) {
        mItems = new ArrayList<>(items);
        mRemoveOnSubmit = removeOnSubmit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create (via inflation) a new view, create a new view holder to contain it
        // and return that view holder.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_bucket_tile, parent, false);

        final ViewHolder vh = new ViewHolder(view);

        vh.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(vh.LTAG, "On click event!");

                final View v = view.inflate(view.getContext(), R.layout.dialog_bucket_tile_enter_initiative, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Enter initiative value")
                        .setView(v)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d(vh.LTAG, "view with name: " + vh.mNameView.getText().toString() +
                                        " submitted initiative: " + ((EditText) v.findViewById(R.id.edit_text)).getText().toString());

                                int initiative = Integer.parseInt(((EditText) v.findViewById(R.id.edit_text)).getText().toString());
                                vh.mCharacterItem.mInitiative = initiative;

                                // Update the persistance table
                                Persist.markActive(vh.mCharacterItem);

                                // Remove from adapter's contents if necessary
                                if (BucketRecyclerAdapter.this.mRemoveOnSubmit) {
                                    BucketRecyclerAdapter.this.mItems.remove(vh.mCharacterItem);
                                    BucketRecyclerAdapter.this.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d(vh.LTAG, "canceled alert dialog.");
                            }
                        });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        vh.mCardView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                Log.d(vh.LTAG, "On drag event captured!");
                return false;
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // this is called when a view holder is being recyled to a new set of data.
        // the new data is at 'position' (indexed by however we're ordering data)
        // and holder is the ViewHolder instance being re-assigned.
        // this may also be called when an empty view holder is first utilized.
        Log.d(LTAG, "Binding view holder to item with name: " + mItems.get(position).mCharacterName);
        holder.setCharacterItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
