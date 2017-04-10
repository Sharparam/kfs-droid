package com.sharparam.kfs

import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.sharparam.kfs.api.Movie
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.content_movie.*

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val id = intent.getIntExtra("id", -1)

        if (id == -1) {
            Toast.makeText(this, "Invalid movie", Toast.LENGTH_SHORT).show()
            return
        }

        object : AsyncTask<Int, Void, Movie>() {
            override fun doInBackground(vararg params: Int?): Movie {
                return Movie.findById(params[0]!!)
            }

            override fun onPostExecute(movie: Movie) {
                toolbar_layout.title = movie.title
                original.text = movie.original
                genre.text = movie.genre
                country.text = movie.country
                director.text = movie.director
                year.text = movie.year.toString()
                duration.text = movie.duration.toString()
                description.loadMarkdown(movie.description)

                object : AsyncTask<Movie, Void, Bitmap>() {
                    override fun doInBackground(vararg params: Movie): Bitmap {
                        Log.d(TAG, "doInBackground: Obtaining bitmap")
                        return params[0].posterBitmap!!
                    }

                    override fun onPostExecute(bitmap: Bitmap) {
                        Log.d(TAG, "onPostExecute: Bitmap obtained, setting imageview")
                        poster.setImageBitmap(bitmap)
                    }
                }.execute(movie)
            }
        }.execute(id)
    }

    companion object {
        private val TAG = MovieActivity::class.java.simpleName
    }
}
