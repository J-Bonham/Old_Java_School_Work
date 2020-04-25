// Jeremiah Bonham
// MDF 3 1501
// Week 3 - Widget

package com.fullsail.android.collectionwidgetdemo;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String TAG = "------Main------";
    public static final String ACTION_VIEW_ADD = "ACTION_VIEW_ADD";
    public static ArrayList<Items> itemsArray = new ArrayList<Items>();
    public static ArrayAdapter adapter;

    public static int deleteIndex;
    public static Items itemData;
    public static final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            MainFrag fragment = new MainFrag();
            getFragmentManager().beginTransaction().add(R.id.activity, fragment).commit();



        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addItem) {
            Intent toAdd = new Intent(this, AddActivity.class);
            startActivityForResult(toAdd, REQUEST_CODE);

                   return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        MainFrag mainFrag = (MainFrag)getFragmentManager().findFragmentById(R.id.activity);

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<Items> temp = (ArrayList<Items>) data.getSerializableExtra("itemData");


            if (itemsArray != null) {
                itemsArray.addAll(temp);
                saveData();
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsArray);
                mainFrag.setListAdapter(adapter);
            } else {
                itemsArray = temp;
                saveData();
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsArray);
                mainFrag.setListAdapter(adapter);

            }
        }
    }

    public void saveData() {

        try {
            FileOutputStream fos = openFileOutput("items.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeInt(itemsArray.size());
            for (Items e : itemsArray) {
                oos.writeObject(e);
            }
            oos.close();
            updateAllWidgets();
            Log.i(TAG, "Data Saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void loadData(){

        MainFrag mainFrag = (MainFrag) getFragmentManager().findFragmentById(R.id.activity);

        try {
            FileInputStream fin = openFileInput("items.txt");
            ObjectInputStream oin = new ObjectInputStream(fin);
            int count = oin.readInt();
            itemsArray = new ArrayList<Items>();
            for (int i = 0; i < count; i++)
                itemsArray.add((Items) oin.readObject());
            oin.close();
            updateAllWidgets();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<Items>(this, android.R.layout.simple_list_item_1, itemsArray);
        mainFrag.setListAdapter(adapter);
    }


    private void updateAllWidgets(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, CollectionWidgetProvider.class));
        if (appWidgetIds.length > 0) {
            new CollectionWidgetProvider().onUpdate(this, appWidgetManager, appWidgetIds);
        }
    }


}