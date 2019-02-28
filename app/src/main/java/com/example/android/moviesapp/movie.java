package com.example.android.moviesapp;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Engineer on 1/20/2019.
 */

public class Movie implements Parcelable {
    private String mName;
    private String mPath;
    String releaseDate;
    double voteAverage;
    String plotSynopsis;

    public Movie(String name, String path) {
        mName = name;
        mPath = path;
    }
    public Movie(String title, String imagePath, String release, double voteAverage, String plotSynopsis ) {
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
    public String getReleaseDate(){
        return releaseDate;
    }
    public void setReleaseDate(String date){
        releaseDate = date;
    }
    public double getVoteAverage(){
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
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
        dest.writeString(plotSynopsis);
    }

    //constructor used for parcel
    public Movie(Parcel parcel){
        //read and set saved values from parcel
        mName = parcel.readString();
        mPath = parcel.readString();
        releaseDate = parcel.readString();
        voteAverage = parcel.readDouble();
        plotSynopsis = parcel.readString();
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }
}
