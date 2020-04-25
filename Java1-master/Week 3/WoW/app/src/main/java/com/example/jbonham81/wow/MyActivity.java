// Jeremiah Bonham
// Java 1 1411
// Week 3


package com.example.jbonham81.wow;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;


public class MyActivity extends Activity {

        ListView list;
        Spinner spinner;
        ArrayList<Game> game;
        //create hashmap
        public HashMap<Integer, Game> gameData = new HashMap<Integer, Game>();
        public class CustomAdapter extends BaseAdapter {
        //create keys
        private Integer[] keys;
        //create custom adapter
        public CustomAdapter(HashMap<Integer, Game> data) {
            gameData = data;
            keys = gameData.keySet().toArray(new Integer[data.size()]);
        }

        @Override
        public int getCount() {
            return gameData.size();
        }

        @Override
        public Object getItem(int position) {
            return gameData.get(keys[position]);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Game game = (Game) getItem(position);

            final View result;

                if (convertView == null) {
                    result = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
                } else {
                    result = convertView;
                }

                if (getResources().getConfiguration().orientation == 1) {
                    ((TextView) result.findViewById(R.id.gametext_portrait)).setText(game.getName());
                } else {
                    ((TextView) result.findViewById(R.id.gametext_landscape)).setText(game.getName());
                }

            return result;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        list = (ListView) findViewById(R.id.listofgames);
        spinner = (Spinner) findViewById(R.id.spinner);
        game = new ArrayList<Game>();

        //Create and add Game objects to Hash Map
        Game origWC = new Game();
            origWC.setName("Warcraft");
            origWC.setRelease("2004");
            origWC.setLvlCap("60");
            origWC.setFeatures("Original Release");
        gameData.put(0, origWC);

        Game tbc = new Game();
            tbc.setName("The Burning Crusade");
            tbc.setRelease("2007");
            tbc.setLvlCap("70");
            tbc.setFeatures("New Races: Draenei and Blood Elves");
        gameData.put(1, tbc);

        Game wotlk = new Game();
            wotlk.setName("Wrath of the Lich King");
            wotlk.setRelease("2008");
            wotlk.setLvlCap("80");
            wotlk.setFeatures("New Class: Death Knight");
        gameData.put(2, wotlk);

        Game cata = new Game();
            cata.setName("Cataclysm");
            cata.setRelease("2010");
            cata.setLvlCap("85");
            cata.setFeatures("New Races: Goblin and Worgen");
        gameData.put(3, cata);

        Game mop = new Game();
            mop.setName("Mists of Panderia");
            mop.setRelease("2012");
            mop.setLvlCap("90");
            mop.setFeatures("New Race: Panderen");
        gameData.put(4, mop);

        Game wod = new Game();
            wod.setName("Warlords of Draenor");
            wod.setRelease("2014");
            wod.setLvlCap("100");
            wod.setFeatures("Garrison Building");
        gameData.put(5, wod);


        //check orientation of device, if landscape
        if (getResources().getConfiguration().orientation == 2) {
            list = (ListView) findViewById(R.id.listofgames);
            CustomAdapter adapter = new CustomAdapter(gameData);
            list.setAdapter(adapter);


            //set listview and info
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView gName = (TextView) findViewById(R.id.name);
                    gName.setText(gameData.get(position).getName());
                    TextView gRelease = (TextView) findViewById(R.id.release);
                    gRelease.setText(gameData.get(position).getRelease());
                    TextView gLevel = (TextView) findViewById(R.id.level);
                    gLevel.setText(gameData.get(position).getLvlCap());
                    TextView gFeatures = (TextView) findViewById(R.id.features);
                    gFeatures.setText(gameData.get(position).getFeatures());

                }

            });


        //check orientation of device, if portrait
        } else if (getResources().getConfiguration().orientation == 1 ) {

            //set spinner and info
            spinner = (Spinner) findViewById(R.id.spinner);
            CustomAdapter adapter = new CustomAdapter(gameData);
            spinner.setAdapter(adapter);

            ArrayAdapter planAdapter = ArrayAdapter.createFromResource(
                    this, R.array.gamesArray, android.R.layout.simple_spinner_item);
            planAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(planAdapter);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println("Item Selected");
                    TextView gName = (TextView) findViewById(R.id.name);
                    gName.setText(gameData.get(spinner.getFirstVisiblePosition()).getName());
                    TextView gRelease = (TextView) findViewById(R.id.release);
                    gRelease.setText(gameData.get(spinner.getFirstVisiblePosition()).getRelease());
                    TextView gLevel = (TextView) findViewById(R.id.level);
                    gLevel.setText(gameData.get(spinner.getFirstVisiblePosition()).getLvlCap());
                    TextView gFeatures = (TextView) findViewById(R.id.features);
                    gFeatures.setText(gameData.get(spinner.getFirstVisiblePosition()).getFeatures());

                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                    System.out.println("Nothing Selected");
                }

            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
