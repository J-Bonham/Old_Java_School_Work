// Jeremiah Bonham
// MDF3 1501
// Week 1

package com.example.jbonham81.audioplayer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment fragment = MainFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, MainFragment.TAG).commit();

    }

}
