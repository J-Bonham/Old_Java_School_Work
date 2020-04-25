// Jeremiah Bonham
// MDF3 1501
// Maps and Location App

package com.jbonham81.mappingphotos;

import java.io.Serializable;

public class Data implements Serializable {

    private static final long serialVersionUID = 455465755725865759L;

    private String mName;
    private String mInfo;
    private String mUri;
    private Double mLat;
    private Double mLon;

    //Empty Constructor
    public Data(){
    }

    public Data(String _name, String _info, String _uri, Double _lat, Double _lon){
        mName = _name;
        mInfo    = _info;
        mUri = _uri;
        mLat = _lat;
        mLon = _lon;
    }

    //Getters
    public String getName() { return mName; }
    public String getInfo() { return mInfo; }
    public String getUri() { return mUri; }
    public Double getLatitude() { return mLat; }
    public Double getLongitude() { return mLon; }

    //Setters
    public void setName(String mName) { this.mName = mName; }
    public void setInfo(String mInfo) { this.mInfo = mInfo; }
    public void setUri(String mUri) { this.mUri = mUri; }
    public void setLatitude(Double mLat) { this.mLat = mLat; }
    public void setLongitude(Double mLon) { this.mLon = mLon; }
}