package com.sharparam.kfs;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharparam.kfs.api.Movie;
import com.sharparam.kfs.api.PagedMovies;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MoviesFragment extends Fragment {
    private static final String TAG = MoviesFragment.class.getSimpleName();

    private OnListFragmentInteractionListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_movies, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            final Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView)view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            (new AsyncTask<Void, Void, List<Movie>>() {
                @Override
                protected List<Movie> doInBackground(Void... params) {
                    Log.d(TAG, "doInBackground: Loading movies");
                    PagedMovies movies = PagedMovies.get(0, 10);
                    List<Movie> movieList = new ArrayList<>();
                    for (Movie movie : movies) {
                        movieList.add(movie);
                    }
                    return movieList;
                }

                @Override
                protected void onPostExecute(List<Movie> movies) {
                    Log.d(TAG, "onPostExecute: Movies loaded, updating adapter");
                    recyclerView.setAdapter(new MoviesAdapter(movies, listener));
                    recyclerView.invalidate();
                }
            }).execute();
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener)context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Movie item);
    }
}
