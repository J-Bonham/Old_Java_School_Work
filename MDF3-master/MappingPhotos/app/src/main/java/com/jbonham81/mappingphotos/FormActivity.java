// Jeremiah Bonham
// MDF3 1501
// Maps and Location App

package com.jbonham81.mappingphotos;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class FormActivity extends Activity {

    public Double mLat = null;
    public Double mLong = null;
    //public static final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formactivity);

        Bundle bundle = getIntent().getParcelableExtra("bundle");
        if (bundle != null){
            mLat = bundle.getDouble("lat");
            mLong = bundle.getDouble("lon");
        }

        if (mLat != null){
            Bundle args = new Bundle();
            args.putDouble("lat", mLat);
            args.putDouble("lon", mLong);

            FormFrag formFragment = FormFrag.newInstance();
            formFragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.formContainer, formFragment).commit();

        } else {

            FormFrag formFragment = FormFrag.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.formContainer, formFragment).commit();
        }

    }

}
