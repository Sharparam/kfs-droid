package com.sharparam.kfs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharparam.kfs.MoviesFragment.OnListFragmentInteractionListener;
import com.sharparam.kfs.api.Movie;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Movie} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private final List<Movie> items;
    private final OnListFragmentInteractionListener listener;

    public MoviesAdapter(List<Movie> items, OnListFragmentInteractionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Movie movie = items.get(position);
        holder.item = movie;
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.date.setText(movie.getFormattedDate());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView title;
        public final TextView genre;
        public final TextView date;

        public Movie item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            title = (TextView)view.findViewById(R.id.title);
            genre = (TextView)view.findViewById(R.id.genre);
            date = (TextView)view.findViewById(R.id.date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + title.getText() + "'";
        }
    }
}
