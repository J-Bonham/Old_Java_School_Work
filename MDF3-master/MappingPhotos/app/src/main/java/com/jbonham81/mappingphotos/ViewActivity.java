package com.jbonham81.mappingphotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class ViewActivity extends Activity {

    public Data locationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewactivity);

        locationData = new Data();

        Intent intent = getIntent();
        if (intent != null){
            locationData = (Data)intent.getSerializableExtra("location");
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", locationData);

        ViewFrag frag = ViewFrag.newInstance();
        frag.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.viewContainer, frag).commit();
    }

}
