// Jeremiah Bonham
// Java 1 1411
// Week 2

// Functional Changes

// Add a button that, when clicked, shows every element in the data
// collection in one AlertDialog.

// Make all static (non-user entered) application text support both
// English (en_US) and Spanish (es_US). Use Google Translate to obtain Spanish strings if needed.


// Cosmetic Changes

// Change all Button controls to be TextView controls instead.
// Make their background red.

// Add the app icon to the title of the AlertDialog(s)
// being used in the app. Make sure the icon is the appropriate
// size as to match the size of the title text.


package com.example.jbonham81.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu; 
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;


public class MakeList extends Activity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    //public variables
    TextView userInput;
    TextView numberOfItems;
    TextView lengthOfItems;
    ListView itemList;
    ArrayList<String> itemListArray = new ArrayList<String>();
    ArrayAdapter listArrAdapter;
    HashSet listHash = new HashSet();
    Drawable icon;


    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_list);

        //TextViews
        userInput = (TextView) findViewById(R.id.input);
        numberOfItems = (TextView) findViewById(R.id.number);
        lengthOfItems = (TextView) findViewById(R.id.length);

        //Button and listener
        TextView saveButton = (TextView) findViewById(R.id.save);
        saveButton.setOnClickListener(this);

        //ListView and Array Adapter Setup
        itemList = (ListView) findViewById(R.id.listView);
        listArrAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemListArray);
        itemList.setAdapter(listArrAdapter);
        itemList.setOnItemClickListener(this);

        TextView showAll = (TextView) findViewById(R.id.showall);
        showAll.setOnClickListener(this);


        icon = getResources().getDrawable(R.drawable.ic_icon);


    }


    //onClick
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case (R.id.showall): {
                System.out.println("Showing All!");

                //send array to hash to prevent duplicate entries
                listHash.addAll(itemListArray);
                itemListArray.clear();
                itemListArray.addAll(listHash);

                listArrAdapter.notifyDataSetChanged();

                String eachItem;
                String listOfAll = "";

                for (int i = 0; i < itemListArray.size(); ++i ) {
                    eachItem = itemListArray.get(i);
                    listOfAll += (eachItem + System.getProperty("line.separator"));

                    System.out.print(listOfAll);

                    }


                    AlertDialog.Builder itemAlert = new AlertDialog.Builder(this);
                    itemAlert.setTitle("All Items");
                    itemAlert.setIcon(icon);
                    itemAlert.setMessage(listOfAll);
                    itemAlert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }

                    });
                    itemAlert.show();
                break;
                }


            case (R.id.save): {

                //System.out.println("Adding Item!");

                //Checks for blank field before saving item, and alerts user using toast

                if (userInput.getText().toString().equals("")) {

                        Context context = getApplicationContext();
                        CharSequence text = ("Please enter item");
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    } else {

                    //pull userInput and add to array
                    itemListArray.add(userInput.getText().toString());

                    //send array to hash to prevent duplicate entries
                    //needs work to notify the user that they have entered a duplicate
                    listHash.addAll(itemListArray);
                    itemListArray.clear();
                    itemListArray.addAll(listHash);
                    listArrAdapter.notifyDataSetChanged();

                    //creating int to hold number of items
                    int itemCount = itemListArray.size();
                    numberOfItems.setText("Number of items is " + itemCount);

                    //call average method
                    getAverageLength(itemListArray);

                    //Create Toast Notification to provide feedback upon entering item
                    Context context = getApplicationContext();
                    CharSequence text = (userInput.getText().toString() + " added to list");
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    //reset userinput field
                    userInput.setText("");
                    break;

                }
            }
        }
    }

    public void getAverageLength(ArrayList arrayList) {

        //setup variables to hold int values for math to find average
        int strTotal = 0;
        int strLength = 0;
        int strCount = 0;
        String eachItem;

        //for loop to add all of characters in all strings together
        for (int i = 0; i < arrayList.size(); i++ ) {
            eachItem = itemListArray.get(i);
            strLength = eachItem.length();
            strTotal += strLength;
            strCount = i + 1;
         }

        //main math of average - total length of all strings divided by number of strings
        strLength = strTotal/strCount;

        //sending average to display
        lengthOfItems.setText("Average Length of Text is " + strLength);

    }

    //Method to create system
    @Override
    public void onItemClick(AdapterView <?> adapterView, View view, int position, long id) {

        AlertDialog.Builder itemAlert = new AlertDialog.Builder(this);
        itemAlert.setTitle("Item Selected");
        itemAlert.setIcon(icon);
        itemAlert.setMessage("You have chosen " + itemListArray.get(position));
        itemAlert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }

        });

        itemAlert.show();

    }


    //onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.make_list, menu);
        return true;
    }

    //onOptionsItemSelected
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
