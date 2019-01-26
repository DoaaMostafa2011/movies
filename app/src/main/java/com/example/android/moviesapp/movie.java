package com.example.android.moviesapp;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Property;

import java.util.Date;

/**
 * Created by Engineer on 1/20/2019.
 */

public class movie implements Parcelable {
    private String mName;
    private String mPath;
    Date releaseDate;
    float voteAverage;
    String plotSynopsis;

    public movie(String name, String path) {
        mName = name;
        mPath = path;
    }
    public movie(String title, String imagePath, Date release, float voteAverage,String plotSynopsis ) {
        mName = title;
        mPath = imagePath;
        releaseDate = release;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
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
    public Date getReleaseDate(){
        return releaseDate;
    }
    public void setReleaseDate(Date date){
        releaseDate = date;
    }
    public float getVoteAverage(){
        return voteAverage;
    }
    public void setVoteAverage(float voteAverage){
         this.voteAverage = voteAverage;
    }
    public String getPlotSynopsis(){
        return plotSynopsis;
    }
    public void setPlotSynopsis(String plotSynopsis){
        this.plotSynopsis = plotSynopsis;
    }


    //write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags){
        //write all properties to the parcle
        dest.writeString(mName);
        dest.writeString(mPath);
        dest.writeLong(releaseDate.getTime());
        dest.writeFloat(voteAverage);
        dest.writeString(plotSynopsis);
    }

    //constructor used for parcel
    public movie(Parcel parcel){
        //read and set saved values from parcel
        mName = parcel.readString();
        mPath = parcel.readString();
        releaseDate = new Date(parcel.readLong());
        voteAverage = parcel.readFloat();
        plotSynopsis = parcel.readString();
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<movie> CREATOR = new Parcelable.Creator<movie>(){

        @Override
        public movie createFromParcel(Parcel parcel) {
            return new movie(parcel);
        }

        @Override
        public movie[] newArray(int size) {
            return new movie[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }
}
