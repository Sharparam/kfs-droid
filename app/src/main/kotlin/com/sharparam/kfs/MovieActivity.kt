package com.sharparam.kfs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sharparam.kfs.api.Movie
import com.sharparam.kfs.api.VolleyWrapper
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

        VolleyWrapper.getInstance(ctx).addToRequestQueue(Movie.generateRequest(id, {
            toolbar_layout.title = it.title
            original.text = it.original
            genre.text = it.genre
            country.text = it.country
            director.text = it.director
            year.text = it.year.toString()
            duration.text = it.duration.toString()
            description.loadMarkdown(it.description)

            poster.setImageUrl(it.posterUrl.toString(), VolleyWrapper.getInstance(ctx).imageLoader)
        }, {
            toast("Failed to load movie")
        }))
    }
}
