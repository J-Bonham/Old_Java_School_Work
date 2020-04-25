package com.example.jbonham81.java2_week2.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.jbonham81.java2_week2.Network.ConnectivityCheck;
import com.example.jbonham81.java2_week2.R;

import java.io.File;

public class SettingsFrag extends PreferenceFragment {

    public static final String TAG = "------------ Settings Frag ------------";

    ConnectivityCheck networkCheck;
    String selectedState;
    String clearCache;

    public SettingsFrag(){ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        networkCheck = new ConnectivityCheck();
        final Preference connection = findPreference("PREF_CONNECTION");
        connection.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                selectedState = prefs.getString("PREF_LIST", "Not Assigned");
                clearCache = prefs.getString("PREF_CONNECTION", "CACHE CLEARED");
                networkCheck = new ConnectivityCheck();
                connection.setTitle(clearCache);
                Log.i(TAG, selectedState);

                return false;
            }
        });

        Preference pref = findPreference("PREF_CONNECTION");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                File dir = getActivity().getFilesDir();
                for (File file: dir.listFiles()) {
                    if (file.isDirectory()) file.delete();
                    file.delete();
                }

                return false;
            }
        });
    }
}