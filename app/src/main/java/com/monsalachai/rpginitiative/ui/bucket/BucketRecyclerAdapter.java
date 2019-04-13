package com.monsalachai.rpginitiative.ui.bucket;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.monsalachai.rpginitiative.R;
import com.monsalachai.rpginitiative.model.CharacterItem;

import java.util.ArrayList;
import java.util.List;

public class BucketRecyclerAdapter extends RecyclerView.Adapter<BucketRecyclerAdapter.ViewHolder> {
    final static String LTAG = "BRA";
    protected List<CharacterItem> mItems;

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mNameView;
        ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
        }

        void setNameText(String name) {
            mNameView.setText(name);
        }
    }

    public BucketRecyclerAdapter(List<CharacterItem> items) {
        mItems = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create (via inflation) a new view, create a new view holder to contain it
        // and return that view holder.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_bucket_tile, parent, false);

        // want to add slide/hold/double-tap/etc listeners here?
        Log.d(LTAG, "Creating ViewHolder for new Bucket Tile View.");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // this is called when a view holder is being recyled to a new set of data.
        // the new data is at 'position' (indexed by however we're ordering data)
        // and holder is the ViewHolder instance being re-assigned.
        // this may also be called when an empty view holder is first utilized.
        Log.d(LTAG, "Binding view holder to item with name: " + mItems.get(position).mCharacterName);
        holder.setNameText(mItems.get(position).mCharacterName);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
