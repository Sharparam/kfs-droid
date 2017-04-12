package com.sharparam.kfs

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sharparam.kfs.api.Page
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.uiThread

import org.json.JSONException

import java.io.IOException

import us.feras.mdv.MarkdownView

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)

        val md = view.findViewById(R.id.md_home) as MarkdownView

        doAsync {
            val page = try {
                Page.findByName("home")
            } catch (e: IOException) {
                Page.generateErrorPage(e)
            } catch (e: JSONException) {
                Page.generateErrorPage(e)
            }

            onUiThread {
                md.loadMarkdown(page.content)
            }
        }

        return view
    }
}
