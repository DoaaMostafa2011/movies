package com.example.android.moviesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Engineer on 1/21/2019.
 */

public class movieListFetcher {
    public static final String API_KEY_STRING = "?api_key=5c1e3e7fda53000aca69577d6f8e7353";
    public static final String TOP_RATED_URL_STRING = "http://api.themoviedb.org/3/Movie/top_rated";
    public static final String MOST_POPULAR_URL_STRING = "http://api.themoviedb.org/3/Movie/popular";
    public static final String POSTER_BASE_URL_STRING = "http://image.tmdb.org/t/p/";
    public static final String POSTER_SIZE_W185 = "w185";
    public String request = "https://api.themoviedb.org/3/Movie/550?api_key=5c1e3e7fda53000aca69577d6f8e7353";
    public enum listType  {TOP_RATED, MOST_POPULAR, FAVORITES}

private String getURLResult(String urlString){
    StringBuilder urlResult = new StringBuilder();
    try {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = connection.getInputStream();
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException(connection.getResponseMessage() + ": with " + urlString);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        String currentLine;

        while ((currentLine = in.readLine()) != null)
            urlResult.append(currentLine);
        inputStream.close();
        in.close();

        connection.disconnect();
    }catch (IOException e) {
        e.printStackTrace();
    }
    return urlResult.toString() ;
}
    public ArrayList<Movie> getMostPopularList() {
        return getMovieList(listType.MOST_POPULAR);
    }

    public ArrayList<Movie> getTopRatedList() {
        return getMovieList(listType.TOP_RATED);
    }

    public ArrayList<Movie> getFavorites() { return getMovieList(listType.FAVORITES); }

    private ArrayList<Movie> getMovieList (listType type) {
    ArrayList<Movie> chosenList = new ArrayList<>();
    String jsonString = "there is no internet connection";
    switch (type) {
        case TOP_RATED:
            jsonString = getURLResult(TOP_RATED_URL_STRING + API_KEY_STRING);
            break;
        case MOST_POPULAR:
            jsonString = getURLResult(MOST_POPULAR_URL_STRING + API_KEY_STRING);
            break;
        case FAVORITES:
            // TODO RETURN arraylist of movies using asyncTaskLoader from database
            break;
    }
    try {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        for (int i = 0; i < jsonArray.length(); i++) {
            String posterPath = jsonArray.getJSONObject(i).getString("poster_path");
            String title =  jsonArray.getJSONObject(i).getString("original_title");
            String date = jsonArray.getJSONObject(i).getString("release_date");
            double voteAverage = jsonArray.getJSONObject(i).getDouble("vote_average");
            String overView = jsonArray.getJSONObject(i).getString("overview");
            chosenList.add(new Movie(title,posterPath,date,voteAverage,overView));
        }
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return chosenList;
 }
}
