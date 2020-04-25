package com.example.jbonham81.java2_week2.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jbonham81.java2_week2.Fragments.DetailFrag;
import com.example.jbonham81.java2_week2.Fragments.ListFrag;
import com.example.jbonham81.java2_week2.Fragments.SpinnerFrag;
import com.example.jbonham81.java2_week2.Movie.Movie;
import com.example.jbonham81.java2_week2.Network.ConnectivityCheck;
import com.example.jbonham81.java2_week2.R;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends Activity implements DataInterface {

    public static final String TAG = "------------ Main ------------";
    private FragmentManager fragmentManager;
    public static final String KEY_DISPLAY = "DisplayText";

    ConnectivityCheck networkCheck;
    Async_Task task;
    Movie movieObj;
    ProgressDialog dialog;
    String totalAppend;
    String selectedState;
    String movieSelected;
    String itemSelectedPass;
    String countArray [];
    File file;

    public String movieTitle;
    public String movieYear;
    public String movieDirector;
    public String movieWriter;
    public String movieGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkCheck = new ConnectivityCheck();
        fragmentManager = getFragmentManager();

        if (findViewById(R.id.selectionContainer).getTag().equals("portrait")) {
            fragmentManager.beginTransaction() .replace(R.id.selectionContainer, SpinnerFrag.newInstance(), SpinnerFrag.TAG) .commit();
        }else{

            fragmentManager.beginTransaction() .replace(R.id.selectionContainer, ListFrag.newInstance(), ListFrag.TAG) .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
        return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getInfo(String selector) {

        task = new Async_Task();
        itemSelectedPass = selector;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        selectedState = prefs.getString("PREF_LIST", "Not Assigned");

        if (networkCheck.Connected(this).equals("offline")) {

            fragmentManager.beginTransaction().replace(R.id.detailContainer, DetailFrag.newInstance(ReadingFromLocal(selector)), DetailFrag.TAG).commit();
            if (movieObj == null){
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("No Connection Detected");
                dialog.setMessage("Information may not be up to date, check connection settings");
                dialog.show();
            }

        } else {

            if (selectedState.equals("Local Storage")) {
                Log.d("MAIN ACTIVITY", "Local Storage");

                fragmentManager.beginTransaction().replace(R.id.detailContainer, DetailFrag.newInstance(ReadingFromLocal(selector)), DetailFrag.TAG).commit();
                if (movieObj == null){

                }

            } else if (selectedState.equals("WiFi Connection")) {

                Log.d("MAIN ACTIVITY", "WiFi Connection");
                if (networkCheck.Connected(this).equals("wifi")) {

                    movieSelected = selector;
                    Log.d(TAG, selector);
                    String urlBegin = "http://www.omdbapi.com/?t=";
                    String urlEnd = "&y=&plot=short&r=json";
                    try {
                        String stringFix = URLEncoder.encode(movieSelected, "UTF-8");
                        if (stringFix.contains("+")) {
                            stringFix = stringFix.replace("+", "%20");
                            Log.d(TAG, "" + stringFix);
                            totalAppend = urlBegin + stringFix + urlEnd;
                            Log.d("APPENDED STRING", totalAppend);
                        }else{
                            totalAppend = urlBegin + movieSelected + urlEnd;
                            Log.d("APPENDED STRING", totalAppend);
                        }

                        task.execute(totalAppend);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("MAIN ACTIVITY", "DATABASE Connection");

                    fragmentManager.beginTransaction().replace(R.id.detailContainer, DetailFrag.newInstance(ReadingFromLocal(selector)), DetailFrag.TAG).commit();

                    if (movieObj == null){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                        dialog.setTitle("No information was saved");
                        dialog.setMessage("Turn WiFi on in settings");
                        dialog.show();
                    }
                }
            }
        }
    }

    public class Async_Task extends AsyncTask<String, Integer, Movie> {

        public static final String TAG = "ASYNC";
        public ArrayList<Movie> objects = new ArrayList<Movie>();
        protected String movieJSON;
        private HttpURLConnection connection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setIndeterminate(false);
            dialog.show();
        }

        @Override
        protected Movie doInBackground(String... strings) {
            try{

                URL movieURL = new URL(strings[0]);
                connection = (HttpURLConnection) movieURL.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                movieJSON = IOUtils.toString(inputStream);
                inputStream.close();
                JSONObject outerOBJ = new JSONObject(movieJSON);

                movieTitle = outerOBJ.getString("Title");
                movieYear = outerOBJ.getString("Year");
                movieDirector = outerOBJ.getString("Director");
                movieWriter = outerOBJ.getString("Writer");
                movieGenre = outerOBJ.getString("Genre");
                countArray = new String[] {movieTitle, movieYear, movieDirector, movieWriter, movieGenre};

                for (int counter = 0; counter < countArray.length; counter++) {

                    dialog.setMax(countArray.length);
                    Thread.sleep(300);
                    publishProgress(counter);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }

            movieObj = new Movie(movieTitle, movieYear, movieDirector, movieWriter, movieGenre);
            return movieObj;
        }


        @Override
        protected void onProgressUpdate(Integer... val) {
            super.onProgressUpdate(val);
            dialog.setProgress(val[0]);
        }

        @Override
        protected void onPostExecute(Movie objects) {
            super.onPostExecute(objects);

            objects = movieObj;
            dialog.dismiss();

            try {
                file = getExternalFilesDir(null);
                FileOutputStream fileOutput = openFileOutput(objects.getMovieTitle() + ".dat", Context.MODE_PRIVATE);
                ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
                objectOutput.writeObject(objects);
                objectOutput.close();

                Log.d("ABSOLUTE PATH", file.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
            }

            fragmentManager.beginTransaction().replace(R.id.detailContainer, DetailFrag.newInstance(movieObj), DetailFrag.TAG) .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }

    public Movie ReadingFromLocal (String ticker) {

        String dat = ticker + ".dat";

        try {

            FileInputStream fileInputStream = openFileInput(dat);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Movie movie = (Movie) objectInputStream.readObject();
            objectInputStream.close();
            return movie;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (findViewById(R.id.selectionContainer).getTag().equals("portrait")) {
            Spinner spin = (Spinner) findViewById(R.id.spinner);
            int selected = spin.getSelectedItemPosition();
            outState.putInt(KEY_DISPLAY, selected);
        } else {
            TextView name = (TextView)findViewById(R.id.titleText);
            ListView lv = (ListView) findViewById(R.id.list);
            ArrayAdapter<String> arrayAD = (ArrayAdapter<String>)lv.getAdapter();
            int holder = arrayAD.getPosition(name.getText().toString());
            Log.d("LIST_KEY", "" + holder);
            outState.putInt(KEY_DISPLAY, holder);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        if (findViewById(R.id.selectionContainer).getTag().equals("portrait")) {
            Spinner spin = (Spinner) findViewById(R.id.spinner);
            Log.d("SPIN_KEY", "" + savedInstanceState.getInt(KEY_DISPLAY));
            spin.setSelection(savedInstanceState.getInt(KEY_DISPLAY));
        }

        super.onRestoreInstanceState(savedInstanceState);

    }


}
