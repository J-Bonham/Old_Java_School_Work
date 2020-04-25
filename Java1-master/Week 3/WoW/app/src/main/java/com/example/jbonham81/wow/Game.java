// Jeremiah Bonham
// Java 1 1411
// Week 3

package com.example.jbonham81.wow;

/**
 * Created by jbonham81 on 11/9/14.
 */

//custom class
public class Game {
        private String name;
        private String release;
        private String lvlCap;
        private String features;

    //Create getters
    public String getName() {
        return name;
    }

    public String getRelease() {
        return release;
    }

    public String getLvlCap() {
        return lvlCap;
    }

    public String getFeatures() {
        return features;
    }

    //Create setters
    public void setName(String name) {
        this.name = name;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setLvlCap(String lvlCap) {
        this.lvlCap = lvlCap;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}
