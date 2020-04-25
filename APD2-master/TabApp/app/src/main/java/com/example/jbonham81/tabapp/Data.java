// Jeremiah Bonham
// 3D Printing Companion

package com.example.jbonham81.tabapp;

import java.io.Serializable;

public class Data implements Serializable {

    private static final long serialVersionUID = 655465755725865749L;

    private String mName;
    private String mUri;
    private String mTime;
    private String mMat;
    private String mColor;
    private String mAmount;
    private String mNotes;

    public Data(){
    }


    public Data(String _name, String _uri, String _time, String _mat, String _color, String _amount, String _notes){
        mName = _name;
        mUri = _uri;
        mTime = _time;
        mMat = _mat;
        mColor = _color;
        mAmount = _amount;
        mNotes = _notes;

    }

    //Getters
    public String getName() { return mName; }
    public String getUri() { return mUri; }
    public String getTime() { return mTime; }
    public String getMat() { return mMat; }
    public String getColor() { return mColor; }
    public String getAmount() { return mAmount; }
    public String getNotes() { return mNotes; }

    //Setters
    public void setName(String mName) { this.mName = mName; }
    public void setUri(String mUri) { this.mUri = mUri; }
    public void setTime(String mTime) { this.mTime = mTime; }
    public void setMat(String mMat) { this.mMat = mMat; }
    public void setColor(String mColor) { this.mColor = mColor; }
    public void setAmount(String mAmount) { this.mAmount = mAmount; }
    public void setNotes(String mNotes) { this.mNotes = mNotes; }

}
