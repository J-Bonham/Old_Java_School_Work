// Jeremiah Bonham
// MDF3 1501
// Maps and Location App

package com.jbonham81.mappingphotos;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewFrag extends Fragment {

    Data locationData;

    TextView locationName;
    TextView locationInfo;
    ImageView locationImage;

    String imageURI;
    Uri uriString;

    //Empty Constructor
    public ViewFrag() {
    }

    public static ViewFrag newInstance(){
        ViewFrag fragment = new ViewFrag();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        locationData = new Data();
        locationData = (Data) getArguments().getSerializable("data");

        return inflater.inflate(R.layout.viewfrag, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        locationName = (TextView)getActivity().findViewById(R.id.nameText);
        locationInfo = (TextView)getActivity().findViewById(R.id.infoText);
        locationImage = (ImageView)getActivity().findViewById(R.id.locationImage);
        locationName.setText(locationData.getName().toString());
        locationInfo.setText(locationData.getInfo().toString());
        imageURI = locationData.getUri();
        uriString = Uri.parse(imageURI);
        locationImage.setImageBitmap(BitmapFactory.decodeFile(uriString.getPath()));

    }


}
