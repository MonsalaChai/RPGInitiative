package com.monsalachai.rpginitiative;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.monsalachai.rpginitiative.dummy.DummyContent;
import com.monsalachai.rpginitiative.ui.main.MainFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CharacterInfoFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ArrayList<DummyContent.DummyItem> items = DummyContent.ITEMS;
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    //.replace(R.id.container, MainFragment.newInstance())
                    .replace(R.id.container, CharacterInfoFragment.newInstance(1, items))
                    .commitNow();
        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.i("fragment", "item " + item);
    }
}
