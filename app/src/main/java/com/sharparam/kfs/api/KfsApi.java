package com.sharparam.kfs.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public final class KfsApi {
    private static final String TAG = KfsApi.class.getSimpleName();

    private static final String ENDPOINT = "http://kristinehamns-filmstudio.se/api.php";

    private static final String ENCODING = "UTF-8";

    private KfsApi() {
    }

    public static String request(final List<Pair<String, String>> params) throws IOException {
        Uri.Builder b = Uri.parse(ENDPOINT).buildUpon();
        for (Pair<String, String> param : params) {
            b.appendQueryParameter(param.first, param.second);
        }

        Uri uri = b.build();
        URL url = new URL(uri.toString());

        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection)url.openConnection();
            return streamToString(conn.getInputStream());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public static String request() throws IOException {
        URL url = new URL(ENDPOINT);

        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection)url.openConnection();
            return streamToString(conn.getInputStream());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public static URL getImageUrl(Movie movie) throws MalformedURLException {
        return getImageUrl(movie.getImage());
    }

    public static URL getImageUrl(String image) throws MalformedURLException {
        return new URL("http", "kristinehamns-filmstudio.se", "/images/movies/" + image);
    }

    public static Bitmap downloadImage(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.connect();
            InputStream input = conn.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            Log.e(TAG, "downloadImage: Failed to download image", e);
            return null;
        }
    }

    public static Bitmap getPoster(String file) {
        try {
            return downloadImage(getImageUrl(file));
        } catch (MalformedURLException e) {
            Log.e(TAG, "getPoster: Invalid URL", e);
            return null;
        }
    }

    private static String streamToString(InputStream stream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int length;

        while ((length = stream.read(buffer)) != -1) {
            outStream.write(buffer, 0, length);
        }

        return outStream.toString(ENCODING);
    }
}
