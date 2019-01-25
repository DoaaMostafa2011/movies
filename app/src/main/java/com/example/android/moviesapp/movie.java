package com.example.android.moviesapp;
import java.util.Date;

/**
 * Created by Engineer on 1/20/2019.
 */

public class movie {
    private String mName;
    private String mPath;
    public movie(String name, String path) {
        mName = name;
        mPath = path;
    }
    public String getName(){
        return mName;
    }
    public void setname(String name){
        mName = name;
    }
    public String getPath(){
        return mPath;
    }
    public void setPath(String path) {
        this.mPath = path;
    }
    
}
