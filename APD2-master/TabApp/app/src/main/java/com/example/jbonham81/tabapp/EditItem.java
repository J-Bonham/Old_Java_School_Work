// Jeremiah Bonham
// 3D Printing Companion

package com.example.jbonham81.tabapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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


public class EditItem extends Fragment {

    Spinner spinner;
    Spinner spinner2;

    String mats;
    String time;

    Button takePhoto;
    Button saveButton;

    EditText titleText;
    EditText colorText;
    EditText amountText;
    EditText notesText;

    Data data;
    Uri mImageUri;
    ImageView imageView;
    ArrayList<Data> itemData;
    String SELCTED_SCENE_KEY="SELCTED_SCENE";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int REQUEST_TAKE_PICTURE = 0x10101;

    public static EditItem newInstance(int sectionNumber) {
        EditItem fragment = new EditItem();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public EditItem() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit, container, false);

        mats = "";
        time = "";

        takePhoto = (Button) view.findViewById(R.id.takePhoto);
        saveButton = (Button) view.findViewById(R.id.editButton);
        imageView = (ImageView)view.findViewById(R.id.photo);

        titleText = (EditText)view.findViewById(R.id.titleText);
        colorText = (EditText)view.findViewById(R.id.colorText);
        amountText = (EditText)view.findViewById(R.id.amountText);
        notesText = (EditText)view.findViewById(R.id.noteText);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.times, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.materials, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        data = new Data();
        loadData();
        if (itemData == null){
            itemData = new ArrayList<Data>();
        }

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

                String name = titleText.getText().toString();
                String color = colorText.getText().toString();
                String amount = amountText.getText().toString();
                String notesVal = notesText.getText().toString();

                String nameTest = "";

                if (name.length() != 0 && imageView.getDrawable() != null &&
                        !name.matches(nameTest))
                        {

                    data.setUri(mImageUri.toString());
                    data.setName(name);
                    data.setColor(color);
                    data.setAmount(amount);
                    data.setAmount(notesVal.toString());
                    data.setTime(time.toString());
                    data.setMat(mats.toString());
                    }

                itemData.add(data);

                    try{
                        FileOutputStream fos = getActivity().openFileOutput("data.txt", getActivity().MODE_PRIVATE);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeInt(itemData.size());

                        for (Data e:itemData){
                            oos.writeObject(e);
                        }
                        oos.close();

                        Toast.makeText(getActivity().getApplicationContext(), "Saving Data.", Toast.LENGTH_LONG).show();
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    finish();
                }
                    });

    }

    private void addImageToGallery(Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        getActivity().sendBroadcast(scanIntent);
    }


    private Uri getOutputUri() {
        String imageName = new SimpleDateFormat("MMddyyy_HHmmss").format(new Date(System.currentTimeMillis()));

        File imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File appDir = new File(imageDir, "tabapp");
        appDir.mkdirs();

        File image = new File(appDir, imageName + ".jpg");
        try {
            image.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return Uri.fromFile(image);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PICTURE && resultCode != getActivity().RESULT_CANCELED) {
            if (mImageUri != null) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(mImageUri.getPath()));
                addImageToGallery(mImageUri);
            } else {
                imageView.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
            }
        }
    }

    private void finish() {
        Intent data = new Intent();
        getActivity().setResult(Activity.RESULT_OK, data);
        //super.getActivity().finish();
        //loadData();

    }

    public void loadData(){
        try{
            FileInputStream fin = getActivity().openFileInput("data.txt");
            ObjectInputStream oin = new ObjectInputStream(fin);
            int count = oin.readInt();
            itemData = new ArrayList<Data>();
            for (int i = 0; i < count; i++)
                itemData.add((Data) oin.readObject());
            oin.close();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }


}
