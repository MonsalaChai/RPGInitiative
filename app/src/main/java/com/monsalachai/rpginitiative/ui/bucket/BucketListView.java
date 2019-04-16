package com.monsalachai.rpginitiative.ui.bucket;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.monsalachai.rpginitiative.R;
import com.monsalachai.rpginitiative.model.CharacterItem;
import com.monsalachai.rpginitiative.persist.Persist;

import java.util.ArrayList;

public class BucketListView extends ConstraintLayout {
    static final String LTAG = "BLV";
    private RecyclerView mCharacterRecycler;
    private RecyclerView mMonsterRecycler;
    private BucketRecyclerAdapter mCharacterAdatper;
    private BucketRecyclerAdapter mMonsterAdapter;
    private ImageButton  mAddButton;
    private ImageButton mRemoveButton;
    private OnBucketListActionListener mActionListener;

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

    public void setActionListener(OnBucketListActionListener listener) {
        mActionListener = listener;
    }

    public OnBucketListActionListener getActionListener() {
        return mActionListener;
    }

    public void update() {
        mCharacterAdatper.mItems = new ArrayList<>(Persist.getAllCharacters("demo"));
        mMonsterAdapter.mItems = new ArrayList<>(Persist.getAllMonsters());
        mCharacterAdatper.notifyDataSetChanged();
        mMonsterAdapter.notifyDataSetChanged();
    }

    private void init() {
        // Set default callback mechanism
        mActionListener = null;

        // Set background.
        setBackgroundResource(R.drawable.drawer_background);
        // inflate the real view, all that jazz.
        inflate(getContext(), R.layout.view_drawer_bucket_list, this);
        mCharacterRecycler = (RecyclerView) getViewById(R.id.drawer_creatures);
        mMonsterRecycler = (RecyclerView) getViewById((R.id.drawer_monsters));
        mAddButton = (ImageButton) getViewById(R.id.bucket_add_button);
        mRemoveButton = (ImageButton) getViewById(R.id.bucket_remove_button);

        // assign adapters to the recyclers.
        mCharacterAdatper = new BucketRecyclerAdapter(Persist.getAllCharacters("demo"), new BucketRecyclerAdapter.OnSubmitCharacterListener() {
            @Override
            public boolean onSubmitCharacterItem(CharacterItem item) {
                if (mActionListener != null)
                    mActionListener.onAddItemToFight(item, false);
                return true;
            }
        });
        mMonsterAdapter = new BucketRecyclerAdapter(Persist.getAllMonsters(), new BucketRecyclerAdapter.OnSubmitCharacterListener() {
            @Override
            public boolean onSubmitCharacterItem(CharacterItem item) {
                if (mActionListener != null)
                    mActionListener.onAddItemToFight(item, true);
                return false;
            }
        });

        mCharacterRecycler.setAdapter(mCharacterAdatper);
        mMonsterRecycler.setAdapter(mMonsterAdapter);

        // set on click listeners for the buttons, for sanity.
        mAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LTAG, "Add button clicked.");

            }
        });
        mRemoveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LTAG, "Remove Button clicked");
            }
        });

        // notify recyclers of content.
        mCharacterAdatper.notifyDataSetChanged();
        mMonsterAdapter.notifyDataSetChanged();

        // set up swipe stuff.
        SwipeController swipeController = new SwipeController(this, mCharacterRecycler);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(mCharacterRecycler);

        itemTouchHelper = new ItemTouchHelper(new SwipeController(this, mMonsterRecycler, false));
        itemTouchHelper.attachToRecyclerView(mMonsterRecycler);
    }
}

