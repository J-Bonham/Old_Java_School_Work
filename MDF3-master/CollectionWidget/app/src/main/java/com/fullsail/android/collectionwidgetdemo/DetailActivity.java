// Jeremiah Bonham
// MDF 3 1501
// Week 3 - Widget

package com.fullsail.android.collectionwidgetdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends Activity {

    public static final String EXTRA_ITEM = "com.fullsail.android.DetailsActivity.EXTRA_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);


        if (savedInstanceState == null) {
            DetailFrag fragment = new DetailFrag();
            getFragmentManager().beginTransaction().add(R.id.detailcontainer, fragment).commit();
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