package com.sharparam.kfs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharparam.kfs.api.Page;

import org.json.JSONException;

import java.io.IOException;

import us.feras.mdv.MarkdownView;

public class HomeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        final MarkdownView md = (MarkdownView)view.findViewById(R.id.md_home);

        (new AsyncTask<String, Void, Page>() {
            @Override
            protected Page doInBackground(String... params) {
                try {
                    return Page.findByName(params[0]);
                } catch (IOException e) {
                    return Page.generateErrorPage(e);
                } catch (JSONException e) {
                    return Page.generateErrorPage(e);
                }
            }

            @Override
            protected void onPostExecute(Page page) {
                md.loadMarkdown(page.getContent());
            }
        }).execute("home");

        return view;
    }
}
