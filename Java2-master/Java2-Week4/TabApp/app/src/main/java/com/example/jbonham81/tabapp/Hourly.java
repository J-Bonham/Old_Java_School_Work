// Java 2 Week 4
//Jeremiah Bonham

//http://api.wunderground.com/api/c79eea9bfd118f2d/conditions/q/IL/Chicago.json


package com.example.jbonham81.tabapp;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Hourly extends ListFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Hourly newInstance(int sectionNumber) {
        Hourly fragment = new Hourly();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Hourly() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hourly, container, false);
        return rootView;
    }

    GetWeather task;

        public static Hourly newInstance() {
            return new Hourly();
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
                    URL url = new URL("http://api.wunderground.com/api/c79eea9bfd118f2d/hourly/q/IL/Chicago.json");
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
                    JSONArray weather = jsonObject.getJSONArray("hourly_forecast");
                    ArrayList<WeatherData> data = new ArrayList<WeatherData>();

                    for(int i = 0; i < weather.length(); i++) {
                        JSONObject forecast = weather.getJSONObject(i);
                        String hour = forecast.getJSONObject("FCTTIME").getString("civil");
                        String condition = forecast.getString("condition");
                        String temp = forecast.getJSONObject("temp").getString("english");
                        String cTemp = forecast.getJSONObject("temp").getString("metric");

                        data.add(new WeatherData(hour, condition, temp, cTemp));
                    }

                    setListAdapter(new ArrayAdapter<WeatherData>(getActivity(), android.R.layout.simple_list_item_1, data));

                } catch(JSONException e) {
                    e.printStackTrace();
                    setEmptyText("Something Wrong at Hourly");
                }

                task = null;
            }
        }

        private class WeatherData {
            public String time;
            public String forecast;
            public String temp;
            public String cTemp;


            public WeatherData(String _time, String _forecast, String _temp, String _cTemp) {
                time = _time;
                forecast = _forecast;
                temp = _temp;
                cTemp = _cTemp;
            }

            @Override
            public String toString() {
                return time + " - " + forecast + " - " + temp + " F (" + cTemp + " C)";
            }
        }
    }













