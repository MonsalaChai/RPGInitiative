package com.monsalachai.rpginitiative.ui.bucket;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.monsalachai.rpginitiative.CharacterViewModel;
import com.monsalachai.rpginitiative.R;
import com.monsalachai.rpginitiative.model.CharacterItem;
import com.monsalachai.rpginitiative.persist.Persist;

import java.util.HashMap;
import java.util.List;

public class DrawerFragment extends Fragment {
    private static final String LTAG = "DF";
    private BucketListView mBucketListView;
    private CharacterViewModel mViewModel;
    private final HashMap<String, Integer> mMonsterCreationCount = new HashMap<>();

    public DrawerFragment() {
        // Required empty public constructor
    }

    public static DrawerFragment newInstance() {
        DrawerFragment fragment = new DrawerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(LTAG, "DrawerFragment OnCreate");

        // Observe changes to bench team.
        mViewModel = ViewModelProviders.of(getActivity()).get(CharacterViewModel.class);
        mViewModel.getBenchTeam().observe(this, new Observer<List<CharacterItem>>() {
            @Override
            public void onChanged(@Nullable List<CharacterItem> characterItems) {
                if (characterItems != null && mBucketListView != null) {
                    mBucketListView.update(characterItems);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LTAG, "onCreateView");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
        mBucketListView = rootView.findViewById(R.id.bucketlistview);
        mBucketListView.setActionListener(new OnBucketListActionListener() {
            @Override
            public void onAddItemToFight(CharacterItem item, boolean isMonster) {
                if (!isMonster) {
                    Log.d(LTAG, "Moving " + item + " to fight zone");
                    mViewModel.moveToFightTeam(item);
                }
                else {
                    // Clone the item and kick it over to the view model via a different method.
                    int id = mMonsterCreationCount.getOrDefault(item.mCharacterName, 0) + 1;
                    String newName = item.mCharacterName + " [" + id + "]";
                    CharacterItem monster = new CharacterItem(newName, item.mInitiative);

                    mMonsterCreationCount.put(item.mCharacterName, id);

                    mViewModel.addMonsterToFight(monster);
                }
            }

            @Override
            public void onDeleteItemFromBench(CharacterItem item) {
                // remove from the correct adapter, delete from persistence.
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.acitivity_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.populate:
                Persist.populate("demo");
                // Update the view model here.

                return true;
            case R.id.clear:
                Persist.clear("demo");
                // Update the view model here.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
