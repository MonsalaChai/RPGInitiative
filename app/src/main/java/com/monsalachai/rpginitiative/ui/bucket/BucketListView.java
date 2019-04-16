package com.monsalachai.rpginitiative.ui.bucket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.monsalachai.rpginitiative.R;
import com.monsalachai.rpginitiative.model.CharacterItem;
import com.monsalachai.rpginitiative.persist.Persist;

import java.util.Collections;
import java.util.List;

public class BucketListView extends ConstraintLayout {
    static final String LTAG = "BLV";
    private RecyclerView mCharacterRecycler;
    private RecyclerView mMonsterRecycler;
    private BucketRecyclerAdapter mCharacterAdapter;
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

    public void update(List<CharacterItem> items) {
        // Only type of item that should be coming in this way is character items.
        // Update mCharacterAdapter
        Collections.sort(items);
        mCharacterAdapter.mItems = items;
        mCharacterAdapter.notifyDataSetChanged();

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
        mCharacterAdapter = new BucketRecyclerAdapter(Persist.getAllCharacters("demo"), new BucketRecyclerAdapter.OnSubmitCharacterListener() {
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

        Collections.sort(mCharacterAdapter.mItems);
        Collections.sort(mMonsterAdapter.mItems);

        mCharacterRecycler.setAdapter(mCharacterAdapter);
        mMonsterRecycler.setAdapter(mMonsterAdapter);

        // set on click listeners for the buttons, for sanity.
        mAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LTAG, "Add button clicked.");
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final View v = inflate(view.getContext(), R.layout.dialog_bucket_add_item, null);

                builder.setView(v)
                        .setMessage("Add new item")
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean isMonster = ((Switch)v.findViewById(R.id.monster_switch)).isChecked();
                        String name = ((EditText)v.findViewById(R.id.edit_name)).getText().toString();
                        int baseInit;
                        try {
                            baseInit = Integer.parseInt(((EditText)v.findViewById(R.id.edit_base_initiative)).getText().toString());
                        }
                        catch (NumberFormatException e) {
                            baseInit = 0;
                        }

                        // confirm name is unique in respective table.
                        if (Persist.confirmUnique("demo", name, isMonster)) {
                            CharacterItem item = new CharacterItem(name, 0);
                            item.setIsMonster(isMonster);
                            // set base dex here.

                            Log.d(LTAG, "Persisting new item: " + item);

                            Persist.add("demo", item);

                            if (item.isMonster()) {
                                mMonsterAdapter.mItems.add(item);
                                Collections.sort(mMonsterAdapter.mItems);
                                mMonsterAdapter.notifyDataSetChanged();
                            }
                            else if (mActionListener != null) {
                                // is character and callback listener is available.
                                mActionListener.onCreateCharacter(item);
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

                builder.create().show();
            }
        });
        mRemoveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LTAG, "Remove Button clicked");
            }
        });

        // notify recyclers of content.
        mCharacterAdapter.notifyDataSetChanged();
        mMonsterAdapter.notifyDataSetChanged();

        // set up swipe stuff.
        SwipeController swipeController = new SwipeController(this, mCharacterRecycler);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(mCharacterRecycler);

        itemTouchHelper = new ItemTouchHelper(new SwipeController(this, mMonsterRecycler, false));
        itemTouchHelper.attachToRecyclerView(mMonsterRecycler);
    }
}

