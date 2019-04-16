package com.monsalachai.rpginitiative;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.monsalachai.rpginitiative.model.CharacterItem;
import com.monsalachai.rpginitiative.persist.Persist;
import com.monsalachai.rpginitiative.ui.bucket.BucketListView;
import com.monsalachai.rpginitiative.ui.bucket.OnBucketListActionListener;
import com.monsalachai.rpginitiative.ui.cif.CharacterInfoFragment;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<CharacterItem> mCharacters;
    private CharacterViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the persistence API.
        Persist.init(this);

        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // notify the character info fragment of data change.
                Log.d("DrawerToggle", "Drawer closed");
                //mViewModel.moveToFightTeam(mCharacters.get(0));
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ((BucketListView) findViewById(R.id.bucketlistview)).setActionListener(new OnBucketListActionListener() {
            @Override
            public void onAddItemToFight(CharacterItem item, boolean isMonster) {
                if (!isMonster)
                    mViewModel.moveToFightTeam(item);
                else {
                    // Need to clone, add to the view model's data set, then move to fight team.
                }
                // Need to make changes to persistence to reflect movement here.
            }

            @Override
            public void onDeleteItemFromBench(CharacterItem item) {
                // need to remove from teh view model and delete from persistence.
            }
        });

        mCharacters = Persist.getAllCharacters("demo");

        mViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);
        mViewModel.getBenchTeam().observe(this, new Observer<List<CharacterItem>>() {
            @Override
            public void onChanged(@Nullable List<CharacterItem> characterItems) {
                Log.d("MainActivity", "onChanged new bench: " + characterItems);
            }
        });

        mViewModel.initBenchTeam(mCharacters);
        mViewModel.initFightTeam(new ArrayList<CharacterItem>());


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    //.replace(R.id.container, MainFragment.newInstance())
                    .replace(R.id.container, CharacterInfoFragment.newInstance(1))
                    .commitNow();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.acitivity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.populate:
                Persist.populate("demo");
                // update view model thingy or whatever.
                ((BucketListView) findViewById(R.id.bucketlistview)).update();
                return true;
            case R.id.clear:
                Persist.clear("demo");
                ((BucketListView) findViewById(R.id.bucketlistview)).update();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public CharacterViewModel getmViewModel() {
        return mViewModel;
    }
}
