// Jeremiah Bonham
// MDF3 1501
// Maps and Location App

package com.jbonham81.mappingphotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    public static final String TAG = "-----Main-----";
    private static final int REQUEST_CODE = 2;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFrag frag = new MapFrag();
        getFragmentManager().beginTransaction().replace(R.id.maincontainer, frag).commit();

        addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        MapFrag frag = new MapFrag();
        getFragmentManager().beginTransaction().replace(R.id.maincontainer, frag).commit();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                addButton.performClick();
            break;
        }
        return true;
    }
}
