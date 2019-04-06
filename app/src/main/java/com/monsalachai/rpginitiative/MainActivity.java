package com.monsalachai.rpginitiative;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;

import com.monsalachai.rpginitiative.model.CharacterItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CharacterInfoFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        ArrayList<CharacterItem> items = getBasicData();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    //.replace(R.id.container, MainFragment.newInstance())
                    .replace(R.id.container, CharacterInfoFragment.newInstance(1, items))
                    .commitNow();
        }
    }

    @Override
    public void onListFragmentInteraction(CharacterItem item) {
        Log.i("fragment", "item " + item);
    }

    private ArrayList<CharacterItem> getBasicData()
    {
        ArrayList<CharacterItem> items = new ArrayList<>();

        items.add(new CharacterItem("Balinope", 12));
        items.add(new CharacterItem("Broot", 3));
        items.add(new CharacterItem("Zinfandel", 5));
        items.add(new CharacterItem("JimmyJohn", 17));
        items.add(new CharacterItem("Shoeshine", 8));
        items.add(new CharacterItem("Snek", 2));

        return items;
    }
}
