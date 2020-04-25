// Jeremiah Bonham
// Java 1 1411

package com.example.jbonham81.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(this);

        //ListView and Array Adapter Setup
        itemList = (ListView) findViewById(R.id.listView);
        listArrAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemListArray);
        itemList.setAdapter(listArrAdapter);
        itemList.setOnItemClickListener(this);

    }

    //onClick
    @Override
    public void onClick(View view) {

        //Checks for blank field before saving item, and alerts user using toast
        if (userInput.getText().toString().equals("")) {

         //System.out.println("Nope!");
            Context context = getApplicationContext();
            CharSequence text = ("Please enter item");
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {

            //pull userInput  and add to array
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

    //Method to create alert
    @Override
    public void onItemClick(AdapterView <?> adapterView, View view, int position, long id) {

        AlertDialog.Builder itemAlert = new AlertDialog.Builder(this);
        itemAlert.setTitle("Item Selected");
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
