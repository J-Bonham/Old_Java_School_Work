package com.jbonham81.mappingphotos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static com.google.android.gms.maps.CameraUpdateFactory.newCameraPosition;

public class MapFrag extends MapFragment implements GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener, LocationListener {

    public static String TAG = "-----MAPFRAG-----";
    private static final int REQUEST_ENABLE_GPS = 0x02101;

    GoogleMap mMap;
    CameraPosition cameraPosition;
    ArrayList<Data> locations;
    Data location;

    LocationManager mManager;
    Double mLat;
    Double mLon;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        loadData();
        mMap = getMap();
        mManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        enableGps();
        mMap.setMyLocationEnabled(true);

        Location currentLocation = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(11)
                .bearing(0)
                .tilt(15)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (locations != null) {
            for (Data e : locations) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(e.getLatitude(), e.getLongitude())).title(e.getName()));
            }

            mMap.setInfoWindowAdapter(new MarkerAdapter());
            mMap.setOnInfoWindowClickListener(this);
            mMap.setOnMapClickListener(this);
            mMap.setMyLocationEnabled(true);
            if (cameraPosition != null) {
                mMap.animateCamera(newCameraPosition(cameraPosition));
            }
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                Bundle bundle = new Bundle();
                bundle.putDouble("lat", latLng.latitude);
                bundle.putDouble("lon", latLng.longitude);
                Intent intent = new Intent(getActivity(), FormActivity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
        loadData();
        getMap();
    }

    private void enableGps() {
        if(mManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

            Location loc = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(loc != null) {
                mLat = loc.getLatitude();
                mLon = loc.getLongitude();
            }

        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("GPS Not Working")
                    .setMessage("Check GPS settings or move closer to a window if inside.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(settingsIntent, REQUEST_ENABLE_GPS);
                        }

                    })
                    .show();
        }
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {

        for (int i = 0; i < locations.size(); i++){
            location = new Data();
            location = locations.get(i);
            if (location.getName().matches(marker.getTitle())){

                Intent intent = new Intent(getActivity(), ViewActivity.class);
                intent.putExtra("location", location);
                startActivity(intent);
            } else {
                Log.i(TAG, "NOT LOADING OnInfoWindowClick");
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onPause(){
        super.onPause();

        mManager.removeUpdates(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        enableGps();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLat = location.getLatitude();
        mLon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String string, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String string) {

    }

    @Override
    public void onProviderDisabled(String string) {

    }

    private class MarkerAdapter implements GoogleMap.InfoWindowAdapter {
        TextView mText;

        public MarkerAdapter() { mText = new TextView(getActivity()); }

        @Override
        public View getInfoContents(Marker marker) {
            mText.setText(marker.getTitle());
            return mText;
        }

        @Override
        public View getInfoWindow(Marker marker) { return null; }
    }

    public void loadData(){
        try{
            FileInputStream fin = getActivity().openFileInput("data.txt");
            ObjectInputStream oin = new ObjectInputStream(fin);
            int count = oin.readInt();
            locations = new ArrayList<Data>();
            for (int i = 0; i < count; i++)
                locations.add((Data) oin.readObject());
            oin.close();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
