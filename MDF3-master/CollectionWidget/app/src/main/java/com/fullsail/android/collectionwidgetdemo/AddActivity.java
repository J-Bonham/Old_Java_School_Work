// Jeremiah Bonham
// MDF 3 1501
// Week 3 - Widget

package com.fullsail.android.collectionwidgetdemo;

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



}