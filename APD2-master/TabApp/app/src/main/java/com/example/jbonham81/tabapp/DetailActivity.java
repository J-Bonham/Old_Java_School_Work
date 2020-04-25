package com.example.jbonham81.tabapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;


public class DetailActivity extends Activity {
    Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        editButton = (Button) findViewById(R.id.editButton);

        if (savedInstanceState == null) {
            DetailFragment fragment = new DetailFragment();
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
    public void onEditClick(AdapterView<?> arg0, View v, int position,long id) {

        Intent i = new Intent(this,EditItem.class);
        startActivity(i);


    }



}