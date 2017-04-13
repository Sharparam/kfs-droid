package com.sharparam.kfs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sharparam.kfs.api.Page
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.onUiThread
import org.json.JSONException
import java.io.IOException

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)

        doAsync {
            val page = try {
                Page.findByName("home")
            } catch (e: IOException) {
                Page.generateErrorPage(e)
            } catch (e: JSONException) {
                Page.generateErrorPage(e)
            }

            onUiThread {
                md_home.loadMarkdown(page.content)
            }
        }

        return view
    }
}
