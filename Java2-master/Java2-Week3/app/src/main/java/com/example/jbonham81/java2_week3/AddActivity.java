// Java 2 Week 3 1412
// Jeremiah Bonham

package com.example.jbonham81.java2_week3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AddActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        if (savedInstanceState == null) {

            AddItemFrag fragment = new AddItemFrag();
            getFragmentManager().beginTransaction().add(R.id.addcontainer, fragment).commit();
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == android.R.id.home) {
            finish();
        }
            return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }


}