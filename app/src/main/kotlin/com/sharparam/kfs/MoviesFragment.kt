package com.sharparam.kfs

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sharparam.kfs.MoviesFragment.OnListFragmentInteractionListener
import com.sharparam.kfs.api.Movie
import com.sharparam.kfs.api.PagedMovies
import com.sharparam.kfs.api.VolleyWrapper
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
class MoviesFragment : Fragment(), AnkoLogger {

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_movies, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            val recyclerView = view
            recyclerView.layoutManager = LinearLayoutManager(context)

            debug("Loading movies")
            VolleyWrapper.getInstance(activity).addToRequestQueue(PagedMovies.generateRequest(0, 10, {
                recyclerView.adapter = MoviesAdapter(it.toList(), listener)
                recyclerView.invalidate()
            }, {
                toast("Failed to load movies")
            }))
        }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context as OnListFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Movie)
    }
}
