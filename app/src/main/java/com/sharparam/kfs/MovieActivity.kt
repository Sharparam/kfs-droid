package com.sharparam.kfs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sharparam.kfs.api.Movie
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.content_movie.*
import org.jetbrains.anko.*

class MovieActivity : AppCompatActivity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra("id", -1)

        if (id == -1) {
            toast("Invalid movie")
            return
        }

        doAsync {
            val movie = Movie.findById(id)

            uiThread {
                toolbar_layout.title = movie.title
                original.text = movie.original
                genre.text = movie.genre
                country.text = movie.country
                director.text = movie.director
                year.text = movie.year.toString()
                duration.text = movie.duration.toString()
                description.loadMarkdown(movie.description)

                doAsync {
                    debug("doInBackground: Obtaining bitmap")
                    val bitmap = movie.posterBitmap

                    uiThread {
                        debug("onPostExecute: Bitmap obtained, setting imageview")
                        poster.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
}
