// Java 2 Week 4
//Jeremiah Bonham

//http://api.wunderground.com/api/c79eea9bfd118f2d/conditions/q/IL/Chicago.json

package com.example.jbonham81.tabapp;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Weekly extends ListFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


    public static Weekly newInstance(int sectionNumber) {
        Weekly fragment = new Weekly();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Weekly() {
    }

    GetWeather task;


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
                URL url = new URL("http://api.wunderground.com/api/41c8bb46090ca744/forecast7day/q/IL/Chicago.json");

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
                JSONObject jsonObject = new JSONObject(s).getJSONObject("forecast").getJSONObject("txt_forecast");
                JSONArray weather = jsonObject.getJSONArray("forecastday");
                ArrayList<Forecast> data = new ArrayList<Forecast>();
                for(int i = 0; i < weather.length(); i++) {
                    JSONObject forecast = weather.getJSONObject(i);
                    String condition = forecast.getString("fcttext");
                    String day = forecast.getString("title");
                    data.add(new Forecast(day, condition));
                }

                setListAdapter(new ArrayAdapter<Forecast>(getActivity(), android.R.layout.simple_list_item_1, data));

            } catch(JSONException e) {
                e.printStackTrace();
                setEmptyText("Problem at Weekly");
            }

            task = null;
        }
    }

    private class Forecast {
        public String time;
        public String forecast;

        public Forecast(String _time, String _forecast) {
            time = _time;
            forecast = _forecast;
        }

        @Override
        public String toString() {
            return time + " : " + forecast;
        }
    }
}



