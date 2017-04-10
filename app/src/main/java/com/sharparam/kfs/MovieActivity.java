package com.sharparam.kfs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sharparam.kfs.api.Movie;

import us.feras.mdv.MarkdownView;

public class MovieActivity extends AppCompatActivity {
    private static final String TAG = MovieActivity.class.getSimpleName();
    
    private ImageView posterView;

    private TextView original_title;

    private TextView genre;

    private TextView country;

    private TextView director;

    private TextView year;

    private TextView duration;

    private MarkdownView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        posterView = (ImageView)findViewById(R.id.poster);
        original_title = (TextView)findViewById(R.id.original);
        genre = (TextView)findViewById(R.id.genre);
        country = (TextView)findViewById(R.id.country);
        director = (TextView)findViewById(R.id.director);
        year = (TextView)findViewById(R.id.year);
        duration = (TextView)findViewById(R.id.duration);
        description = (MarkdownView)findViewById(R.id.description);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        if (id == -1) {
            Toast.makeText(this, "Invalid movie", Toast.LENGTH_SHORT).show();
            return;
        }

        (new AsyncTask<Integer, Void, Movie>() {
            @Override
            protected Movie doInBackground(Integer... params) {
                return Movie.findById(params[0]);
            }

            @Override
            protected void onPostExecute(Movie movie) {
                ((CollapsingToolbarLayout)findViewById(R.id.toolbar_layout)).setTitle(movie.getTitle());
                original_title.setText(movie.getOriginal());
                genre.setText(movie.getGenre());
                country.setText(movie.getCountry());
                director.setText(movie.getDirector());
                year.setText(String.valueOf(movie.getYear()));
                duration.setText(String.valueOf(movie.getDuration()));
                description.loadMarkdown(movie.getDescription());

                (new AsyncTask<Movie, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Movie... params) {
                        Log.d(TAG, "doInBackground: Obtaining bitmap");
                        return params[0].getPosterBitmap();
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        Log.d(TAG, "onPostExecute: Bitmap obtained, setting imageview");
                        posterView.setImageBitmap(bitmap);
                    }
                }).execute(movie);
            }
        }).execute(id);
    }
}
