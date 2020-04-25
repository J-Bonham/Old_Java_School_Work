// Jeremiah Bonham
// 3D Printing Companion
package com.example.jbonham81.tabapp;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    GetWeather task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        task = new GetWeather();
        task.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(task != null) {
            task.cancel(false);
        }
    }

    private class GetWeather extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            task = this;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL("https://api.myjson.com/bins/3wz7v");
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                String data = IOUtils.toString(inputStream);
                inputStream.close();
                httpURLConnection.disconnect();
                return data;

            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject newsItems = jsonObject.getJSONObject("current_observation");
                System.out.println(newsItems);

                ((TextView)getView().findViewById(R.id.news)).setText(newsItems.getString("news"));
                ((TextView)getView().findViewById(R.id.updates)).setText(newsItems.getString("updates"));

            } catch(JSONException e) {
                e.printStackTrace();
            }

            task = null;
        }
    }
}



