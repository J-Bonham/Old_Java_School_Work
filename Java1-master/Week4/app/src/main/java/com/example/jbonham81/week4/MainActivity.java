// Jeremiah Bonham
// Java 1 Week 4 1411

// API Key for quick usage without looking up
// http://api.themoviedb.org/3/search/movie?api_key=4ed229fe2e133f61d3815a1d358e111f&query=fight%20club
//
// 4ed229fe2e133f61d3815a1d358e111f

package com.example.jbonham81.week4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements View.OnClickListener {

    String TAG = "-----------------Testing----------------";
    Button search;
    EditText userInput;
    JSONObject movieData;
    ProgressBar progbar;
    GatherMovieInfo gatherMovieInfo;
    private String userSearch;
    private final String KEY = "title";
    private final String VALUE = "release_date";
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (Button) findViewById(R.id.searchbutton);
        search.setOnClickListener(this);
        userInput = (EditText) findViewById(R.id.userinput);
        progbar = (ProgressBar) findViewById(R.id.progressBar);
        list = (ListView) findViewById(R.id.list);
    }

    //onClick
    @Override
    public void onClick(View view) {
        jsonCall();
    }

    // check for connection using custom class
    private void jsonCall() {

        //grab user input
        userSearch = userInput.getText().toString();
        ConnectivityCheck connectivity = new ConnectivityCheck(getApplicationContext());

        boolean isConnecting = connectivity.isConnected();
        if (isConnecting) {
            System.out.println("------------------Connected------------------");

            //activate progress indicator
            progbar.setVisibility(View.VISIBLE);
            gatherMovieInfo = new GatherMovieInfo();
            gatherMovieInfo.execute();

        } else {
            System.out.println("------------------Not Connected------------------");
            progbar.setVisibility(View.INVISIBLE);
            displayError();

        }

    }

    //private class to run Async to get JSON
    private class GatherMovieInfo extends AsyncTask<Object, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Object[] params) {

            int responseCode;
            JSONObject jsonResponse = null;
            StringBuilder sb = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            UrlEncoder();

            HttpGet httpGet = new HttpGet("http://api.themoviedb.org/3/search/movie?api_key=4ed229fe2e133f61d3815a1d358e111f&query=" + userSearch);

            try {
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                responseCode = statusLine.getStatusCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }

                    jsonResponse = new JSONObject(sb.toString());

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        protected void onPostExecute(JSONObject result) {
            movieData = result;
            handleJSON();
        }

    }


    private void handleJSON () {
        progbar.setVisibility(View.INVISIBLE);

        if (movieData == null) {

            displayError();

        } else {
            try {
                //get JSON Array "results"
                JSONArray jsonPosts = movieData.getJSONArray("results");
                System.out.println("---------------" + jsonPosts + "---------------");
                ArrayList<HashMap<String, String>> apiData = new ArrayList<HashMap<String, String>>();

                //loop through JSON Array
                for (int i = 0; i < jsonPosts.length(); i++) {
                    JSONObject post = jsonPosts.getJSONObject(i);

                    //Get title and release date
                    String title = post.getString(KEY);
                    title = Html.fromHtml(title).toString();
                    String release_date = post.optString(VALUE, "N/A");
                    release_date = Html.fromHtml(release_date).toString();

                    //create hash map that uses title and release date as key, value
                    HashMap<String, String> movieInfo = new HashMap<String, String>();
                    movieInfo.put(KEY, title);
                    movieInfo.put(VALUE, release_date);
                    apiData.add(movieInfo);

                    //System.out.println("---------------" + apiData + "---------------");
                    String[] keys = {KEY, VALUE};
                    int[] items = {android.R.id.text1, android.R.id.text2};
                    SimpleAdapter adapter = new SimpleAdapter(this, apiData, android.R.layout.simple_list_item_2, keys, items);
                    list.setAdapter(adapter);

                }

            } catch (JSONException e) {
                Log.e(TAG, "exception caught:", e);
                displayError();
            }
        }

    }

    //Create Alert when network does not connect
    private void displayError() {

        AlertDialog.Builder itemAlert = new AlertDialog.Builder(this);
        itemAlert.setTitle("No Connection");
        itemAlert.setMessage("Please check your internet connection!");
        itemAlert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {

            }

        });

        itemAlert.show();

    }

    private void UrlEncoder() {
        try {
            userSearch = URLEncoder.encode(userSearch, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}