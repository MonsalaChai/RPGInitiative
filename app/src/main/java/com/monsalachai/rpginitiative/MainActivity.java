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
import android.view.View;

import com.monsalachai.rpginitiative.model.CharacterItem;
import com.monsalachai.rpginitiative.persist.Persist;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<CharacterItem> mCharacters;
    private CharacterViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                mViewModel.moveToFightTeam(mCharacters.get(0));
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mCharacters = Persist.getAllCharacters("");

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
}
