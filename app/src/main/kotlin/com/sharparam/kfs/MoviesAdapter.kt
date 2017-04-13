package com.sharparam.kfs

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sharparam.kfs.MoviesFragment.OnListFragmentInteractionListener
import com.sharparam.kfs.api.Movie
import org.jetbrains.anko.find

/**
 * [RecyclerView.Adapter] that can display a [Movie] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MoviesAdapter(private val items: List<Movie>, private val listener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items[position]

        holder.movie = movie
        holder.title.text = movie.title
        holder.genre.text = movie.genre
        holder.date.text = movie.formattedDate

        holder.view.setOnClickListener {
            listener?.onListFragmentInteraction(holder.movie!!)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title = view.find<TextView>(R.id.title)
        val genre = view.find<TextView>(R.id.genre)
        val date = view.find<TextView>(R.id.date)

        var movie: Movie? = null

        override fun toString(): String = "${super.toString()} '${title.text}'"
    }
}
