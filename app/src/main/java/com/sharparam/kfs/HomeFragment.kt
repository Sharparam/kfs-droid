package com.sharparam.kfs

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sharparam.kfs.api.Page

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

        object : AsyncTask<String, Void, Page>() {
            override fun doInBackground(vararg params: String): Page {
                try {
                    return Page.findByName(params[0])
                } catch (e: IOException) {
                    return Page.generateErrorPage(e)
                } catch (e: JSONException) {
                    return Page.generateErrorPage(e)
                }
            }

            override fun onPostExecute(page: Page) {
                md.loadMarkdown(page.content)
            }
        }.execute("home")

        return view
    }
}
