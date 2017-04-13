package com.sharparam.kfs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sharparam.kfs.api.Page
import com.sharparam.kfs.api.VolleyWrapper
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)

        VolleyWrapper.getInstance(activity).addToRequestQueue(Page.generateRequest("home", {
            md_home.loadMarkdown(it.content)
        }))

        return view
    }
}
