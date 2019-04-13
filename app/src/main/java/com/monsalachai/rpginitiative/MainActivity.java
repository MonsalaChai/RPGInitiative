package com.monsalachai.rpginitiative;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;

import com.monsalachai.rpginitiative.model.CharacterItem;
import com.monsalachai.rpginitiative.persist.Persist;
import com.monsalachai.rpginitiative.ui.main.MainFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<CharacterItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mItems = Persist.getAllCharacters("");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    //.replace(R.id.container, CharacterInfoFragment.newInstance(1, items))
                    .commitNow();
        }
    }

    private void startFight()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.container, CharacterInfoFragment.newInstance(1, mItems));

        transaction.commit();
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

    private ArrayList<CharacterItem> getBasicData()
    {
        ArrayList<CharacterItem> items = new ArrayList<>();

        items.add(new CharacterItem("Balinope", 12));
        items.add(new CharacterItem("Broot", 3));
        items.add(new CharacterItem("Zinfandel", 5));
        items.add(new CharacterItem("Ezreal", 4));
        items.add(new CharacterItem("JimmyJohn", 17));
        items.add(new CharacterItem("Shoeshine", 8));
        items.add(new CharacterItem("Snek", 2));

        return items;
    }

    @Override
    public void onClick(View v) {
        startFight();
    }
}
