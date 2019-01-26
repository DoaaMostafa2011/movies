package com.example.android.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.android.moviesapp.movieListFetcher.listType.MOST_POPULAR;
import static com.example.android.moviesapp.movieListFetcher.listType.TOP_RATED;

public class MainActivity extends AppCompatActivity {
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    public ArrayList<movie> movies  = new ArrayList<>() ;
    private movieListFetcher.listType mCurrentListType = MOST_POPULAR;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecyclerViewAdapter();
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        new LoadMovieList().execute(MOST_POPULAR);
        recyclerView =  findViewById(R.id.rv);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }


    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        return view;
    }*/

    @Override
    protected void onStart() {
        super.onStart();

        movies = new movieListFetcher().getTopRatedList();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_type, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_most_popular:
                if (mCurrentListType == MOST_POPULAR) {
                    // do nothing, do not have to update view
                } else {
                    // update recycler view
                    new LoadMovieList().execute(MOST_POPULAR);
                    mCurrentListType = MOST_POPULAR;
                }
                return true;
            case R.id.menu_item_top_rated:
                if (mCurrentListType == TOP_RATED) {
                    // do nothing, do not have to update view
                } else {
                    // update recycler view
                    new LoadMovieList().execute(TOP_RATED);
                    mCurrentListType = TOP_RATED;
                }
                return true;
        }
        return true;
    }




    class LoadMovieList extends AsyncTask<movieListFetcher.listType, Void, Void> {

        @Override
        protected Void doInBackground(movieListFetcher.listType... params) {
            movies = null;
            switch (params[0]) {
                case TOP_RATED:
                    movies = new movieListFetcher().getTopRatedList();
                    break;
                case MOST_POPULAR:
                    movies = new movieListFetcher().getMostPopularList();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (movies != null) {
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }
        }
    }
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {

        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            int layoutIdForListItem = R.layout.grid_item;
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(layoutIdForListItem, parent, false);
            viewHolder holder = new viewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(viewHolder holder, int position) {
            holder.loadImage(position);
        }

        @Override
        public int getItemCount() {
            if (movies != null) {
                return movies.size();
            } else {
                return 0;
            }
        }


        public class viewHolder extends RecyclerView.ViewHolder {
            ImageView posterView;

            public viewHolder(View view) {
                super(view);
                posterView = view.findViewById(R.id.iv_item);
            }

            public void loadImage(int position) {
                String posterPath = movies.get(position).getPath();
                String fullPath = movieListFetcher.POSTER_BASE_URL_STRING + movieListFetcher.POSTER_SIZE_W185 + posterPath;
                //Log.d(TAG, fullPath);
                Picasso.get().load(fullPath).into(posterView);
                addOnClickListener(position);
            }
            public void addOnClickListener(final int position) {
                posterView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText( MainActivity.this, "You clicked image view # " + position, Toast.LENGTH_SHORT).show();
                        if (position == RecyclerView.NO_POSITION) return;
                        movie movie = adapter.getItem(position);
                        Context context = MainActivity.this;
                        Class destinationActivity = detailActivity.class;
                        Intent intent = new Intent(context,destinationActivity);
                        intent.putExtra("movie", movie);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
