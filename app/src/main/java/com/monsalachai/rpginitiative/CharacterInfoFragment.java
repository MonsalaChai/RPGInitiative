package com.monsalachai.rpginitiative;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.monsalachai.rpginitiative.model.CharacterItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CharacterInfoFragment extends Fragment implements OnListFragmentInteractionListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_ITEMS = "character-items";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private List<CharacterItem> mItems;
    private MyCharacterInfoRecyclerViewAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CharacterInfoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CharacterInfoFragment newInstance(int columnCount, List<CharacterItem> items) {
        CharacterInfoFragment fragment = new CharacterInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putSerializable(ARG_ITEMS, (Serializable) items);  // all standard List implementations
                                                                // implement serializable
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mItems = (List<CharacterItem>) getArguments().get(ARG_ITEMS);
        } else {
            mItems = new ArrayList<>();
        }

        mAdapter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_characterinfo_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            mAdapter = new MyCharacterInfoRecyclerViewAdapter(mItems, this);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // The fragment itself will manage the changes to the view. If there is anything that needs to
    // be communicated back up to the calling Activity, then we will want it to implement this
    // method and then have it connected in the onAttach() method.
    @Override
    public void onListFragmentInteraction(CharacterItem item) {
        Log.i("fragment", "item " + item);
        item.mHoldingTurn = !item.mHoldingTurn;
        mAdapter.update();
    }
}
