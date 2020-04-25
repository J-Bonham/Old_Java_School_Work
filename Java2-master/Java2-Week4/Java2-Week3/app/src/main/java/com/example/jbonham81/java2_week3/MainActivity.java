// Java 2 Week 3 1412
// Jeremiah Bonham

package com.example.jbonham81.java2_week3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends Activity {

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

            FileOutputStream outputStream = openFileOutput("items.data", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            for (int i = 0; i < itemsArray.size(); i++) {

                itemData = itemsArray.get(i);
                objectOutputStream.writeObject(itemData);
            }
            objectOutputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    //@Override
    public void loadData() {

        MainFrag mainFrag = (MainFrag) getFragmentManager().findFragmentById(R.id.activity);

        try {
            FileInputStream inputStream = openFileInput("item.data");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            while (inputStream.available() != 0) {
                itemData = (Items) objectInputStream.readObject();
                itemsArray.add(itemData);
            }
            objectInputStream.close();
            adapter = new ArrayAdapter<Items>(this, android.R.layout.simple_list_item_1, itemsArray);
            mainFrag.setListAdapter(adapter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}