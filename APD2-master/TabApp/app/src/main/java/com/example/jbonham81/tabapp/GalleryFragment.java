// Jeremiah Bonham
// 3D Printing Companion

package com.example.jbonham81.tabapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    //implements AdapterView.OnItemClickListener {

    GridView gridView;

    ArrayList<String> photoString = new ArrayList<String>();
    int image = 0;
    String[] Files;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String POSITIONSELECTED = "";

    public static GalleryFragment newInstance(int sectionNumber) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery, container, false);
        gridView = null;
        loadPhotos();

        gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,long id) {



                Intent i = new Intent(getActivity(),DetailActivity.class);
                i.putExtra(POSITIONSELECTED, position);
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                startActivity(i);


            }
        });



        return view;
    }

    public void loadPhotos() {

        File imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File appDir = new File(imageDir, "tabapp");

        if (appDir.exists()) {
            Files = appDir.list();
            System.out.println(appDir);
            for (int i = 0; i < Files.length; i++) {
                photoString.add(appDir.getAbsolutePath() + File.separator + Files[i]);
            }

        }

    }


    public class ImageAdapter extends BaseAdapter {

        Context context;
        public ImageAdapter(Context c) {
            this.context = c;
        }

        public int getCount() {
            return photoString.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public void setColorType() {
            if (image == 0)
                image = 1;
            else {
                image = 0;
            }

        }

        public View getView(int position, View convertView, ViewGroup parent) {


            ImageView imageView;

            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);

                if (image == 0) {
                    imageView.setBackgroundColor(123456);
                    setColorType();
                } else {
                    setColorType();
                    imageView.setBackgroundColor(123456);
                }

            } else {
                imageView = (ImageView) convertView;

                if (image == 0) {
                    imageView.setBackgroundColor(123456);
                    setColorType();
                }

            }

            Bitmap myBitmap = decodeFile(new File(photoString.get(position)));
            imageView.setImageBitmap(myBitmap);

            return imageView;
        }

        private Bitmap decodeFile(File f){

            try {

                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inSampleSize = 4;
                BitmapFactory.decodeStream(new FileInputStream(f),null,o);

                final int REQUIRED_SIZE=70;

                int scale=1;
                while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
                    scale*=2;

                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = 4;

                return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
            } catch (FileNotFoundException e) {}
            return null;
        }

    }
}
