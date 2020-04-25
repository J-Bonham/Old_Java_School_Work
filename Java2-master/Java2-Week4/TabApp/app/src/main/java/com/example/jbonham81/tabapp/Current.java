// Java 2 Week 4
//Jeremiah Bonham

//http://api.wunderground.com/api/c79eea9bfd118f2d/conditions/q/IL/Chicago.json

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

public class Current extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Current newInstance(int sectionNumber) {
        Current fragment = new Current();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Current() {
    }

    GetWeather task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.current, container, false);
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
                URL url = new URL("http://api.wunderground.com/api/c79eea9bfd118f2d/conditions/q/IL/Chicago.json");

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
                JSONObject weather = jsonObject.getJSONObject("current_observation");

                    ((TextView)getView().findViewById(R.id.temperature)).setText(weather.getString("temperature_string"));
                    ((TextView)getView().findViewById(R.id.condition)).setText(weather.getString("weather"));
                    ((TextView)getView().findViewById(R.id.humidity)).setText(weather.getString("relative_humidity"));
                    ((TextView)getView().findViewById(R.id.wind)).setText(weather.getString("wind_string"));

            } catch(JSONException e) {
                e.printStackTrace();
            }

            task = null;
        }
    }
}
