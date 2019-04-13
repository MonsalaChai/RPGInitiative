package com.monsalachai.rpginitiative.ui.bucket;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.monsalachai.rpginitiative.R;
import com.monsalachai.rpginitiative.persist.Persist;

public class BucketListView extends ConstraintLayout {
    static final String LTAG = "BLV";
    private RecyclerView mCharacterRecycler;
    private RecyclerView mMonsterRecycler;
    private ImageButton  mAddButton;
    private ImageButton mRemoveButton;

    public BucketListView(Context context) {
        super(context);
        init();
    }

    public BucketListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BucketListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // Set background.
        setBackgroundResource(R.drawable.drawer_background);
        // inflate the real view, all that jazz.
        inflate(getContext(), R.layout.view_drawer_bucket_list, this);
        mCharacterRecycler = (RecyclerView) getViewById(R.id.drawer_creatures);
        mMonsterRecycler = (RecyclerView) getViewById((R.id.drawer_monsters));
        mAddButton = (ImageButton) getViewById(R.id.bucket_add_button);
        mRemoveButton = (ImageButton) getViewById(R.id.bucket_remove_button);

        // assign adapters to the recyclers.
        final BucketRecyclerAdapter characterAdapter = new BucketRecyclerAdapter(Persist.getAllCharacters(""));
        final BucketRecyclerAdapter monsterAdapter = new BucketRecyclerAdapter(Persist.getAllMonsters());

        mCharacterRecycler.setAdapter(characterAdapter);
        mMonsterRecycler.setAdapter(monsterAdapter);

        // set on click listeners for the buttons, for sanity.
        mAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LTAG, "Add button clicked.");
                Log.d(LTAG, "There are " + characterAdapter.getItemCount() + " items in the character adapter");
                Log.d(LTAG, "There are " + monsterAdapter.getItemCount() + " items in the monster adapter");

            }
        });
        mRemoveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LTAG, "Remove Button clicked");
                String characterRecyclerSize = "(" + mCharacterRecycler.getWidth() + ", " + mCharacterRecycler.getHeight() + ")";
                String monsterRecyclerSize = "(" + mMonsterRecycler.getWidth() + ", " + mMonsterRecycler.getHeight() + ")";
                Log.d(LTAG, "character recycler size: " + characterRecyclerSize);
                Log.d(LTAG, "monster recycler size: " + monsterRecyclerSize);
            }
        });

        // notify recyclers of content.
        characterAdapter.notifyDataSetChanged();
        monsterAdapter.notifyDataSetChanged();
    }
}

