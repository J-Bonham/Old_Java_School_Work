// Jeremiah Bonham
// MDF3 1501
// Maps and Location App

package com.jbonham81.mappingphotos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FormFrag extends Fragment implements LocationListener{

    public static final String TAG = "-----FORMFRAG-----";
    private static final int REQUEST_TAKE_PICTURE = 0x10101;
    private static final int REQUEST_ENABLE_GPS = 0x02101;

    Button takePhoto;
    Button saveButton;
    EditText mNameText;
    EditText mInfoText;
    Data data;
    Uri mImageUri;
    ImageView imageView;
    ArrayList<Data> locations;
    LocationManager mManager;
    Double mLat;
    Double mLon;
    Double passedLat = null;
    Double passedLon = null;

    //Empty Constructor
    public FormFrag() {
    }

    public static FormFrag newInstance(){
        FormFrag formFrag = new FormFrag();
        return formFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            passedLat = getArguments().getDouble("lat");
            passedLon = getArguments().getDouble("lon");
        } catch (Exception e){
            e.printStackTrace();
        }

        return inflater.inflate(R.layout.formfrag, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        data = new Data();
        mManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        loadData();
        if (locations == null){
            locations = new ArrayList<Data>();
        }


        mNameText = (EditText) view.findViewById(R.id.nameText);
        mInfoText = (EditText) view.findViewById(R.id.infoText);
        takePhoto = (Button) view.findViewById(R.id.photoButton);
        saveButton = (Button) view.findViewById(R.id.saveButton);
        imageView = (ImageView)view.findViewById(R.id.photo);


        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageUri = getOutputUri();
                if (mImageUri != null) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                }
                startActivityForResult(cameraIntent, REQUEST_TAKE_PICTURE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText = mNameText.getText().toString();
                String infoText = mInfoText.getText().toString();
                String nameTest = "Location Name";
                String infoTest = "Location Info";

                // Test to see if all field are completed and image is captured
                if (nameText.length() != 0 && infoText.length() != 0 && imageView.getDrawable() != null &&
                        !nameText.matches(nameTest) && !infoText.matches(infoTest)){


                    data.setName(nameText);
                    data.setInfo(infoText);
                    data.setUri(mImageUri.toString());

                    if (passedLat != null){
                        data.setLatitude(passedLat);
                        data.setLongitude(passedLon);
                    } else {
                        data.setLatitude(mLat);
                        data.setLongitude(mLon);
                    }
                    locations.add(data);


                    try{
                        FileOutputStream fos = getActivity().openFileOutput("data.txt", getActivity().MODE_PRIVATE);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeInt(locations.size());

                        for (Data e:locations){
                            oos.writeObject(e);
                        }
                        oos.close();

                        Toast.makeText(getActivity().getApplicationContext(), "Saving Data.", Toast.LENGTH_LONG).show();
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    finish();
                }
            }
        });

    }

    private Uri getOutputUri(){
        String imageName = new SimpleDateFormat("MMddyyy_HHmmss").format(new Date(System.currentTimeMillis()));

        File imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File appDir = new File(imageDir, "MappingPhotos");
        appDir.mkdirs();

        File image = new File(appDir, imageName + ".jpg");
        try{
            image.createNewFile();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return Uri.fromFile(image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_TAKE_PICTURE && resultCode != getActivity().RESULT_CANCELED) {
            if(mImageUri != null) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(mImageUri.getPath()));
                addImageToGallery(mImageUri);
            } else {
                imageView.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
            }
        }
        enableGps();
    }

    private void addImageToGallery(Uri imageUri){
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        getActivity().sendBroadcast(scanIntent);
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


    private void finish() {
        Intent data = new Intent();
        data.putExtra("returnKey", locations);
        getActivity().setResult(Activity.RESULT_OK, data);
        super.getActivity().finish();
        loadData();
    }


    private void enableGps(){
        if(mManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,10,this);

            Location loc = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null){
                mLat = (loc.getLatitude());
                mLon = (loc.getLongitude());
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

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
}
