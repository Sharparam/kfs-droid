package com.sharparam.kfs.api;

import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Page {
    private final int id;

    private final String name;

    private final String title;

    private final String content;

    private final int sort;

    private final boolean enabled;

    public Page(int id, String name, String title, String content, int sort, boolean enabled) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.sort = sort;
        this.enabled = enabled;
    }

    public static Page findByName(String name) throws IOException, JSONException {
        List<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<>("page", name));
        String json = KfsApi.request(params);
        return fromJsonObject(new JSONObject(json));
    }

    public static Page generateErrorPage(Throwable ex) {
        return new Page(0, "error", "Error", "ERROR: " + ex.getMessage(), 0, true);
    }

    private static Page fromJsonObject(JSONObject obj) throws JSONException {
        return new Page(obj.getInt("id"), obj.getString("name"), obj.getString("title"), obj.getString("content"), obj.getInt("sort"), obj.getBoolean("enabled"));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getSort() {
        return sort;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
