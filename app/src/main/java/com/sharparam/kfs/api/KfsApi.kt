package com.sharparam.kfs.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.util.Pair

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object KfsApi {
    private const val ENDPOINT = "http://kristinehamns-filmstudio.se/api.php"

    private const val ENCODING = "UTF-8"

    private val TAG = KfsApi::class.java.simpleName

    @Throws(IOException::class)
    @JvmStatic fun request(params: List<Pair<String, String>>): String {
        val b = Uri.parse(ENDPOINT).buildUpon()
        for (param in params) {
            b.appendQueryParameter(param.first, param.second)
        }

        val uri = b.build()
        val url = URL(uri.toString())

        var conn: HttpURLConnection? = null

        try {
            conn = url.openConnection() as HttpURLConnection
            return streamToString(conn.inputStream)
        } finally {
            if (conn != null)
                conn.disconnect()
        }
    }

    @Throws(IOException::class)
    @JvmStatic fun request(): String {
        val url = URL(ENDPOINT)

        var conn: HttpURLConnection? = null

        try {
            conn = url.openConnection() as HttpURLConnection
            return streamToString(conn.inputStream)
        } finally {
            if (conn != null)
                conn.disconnect()
        }
    }

    @Throws(MalformedURLException::class)
    @JvmStatic fun getImageUrl(movie: Movie): URL {
        return getImageUrl(movie.image)
    }

    @Throws(MalformedURLException::class)
    @JvmStatic fun getImageUrl(image: String): URL {
        return URL("http", "kristinehamns-filmstudio.se", "/images/movies/" + image)
    }

    @JvmStatic fun downloadImage(url: URL): Bitmap? {
        try {
            val conn = url.openConnection() as HttpURLConnection
            conn.connect()
            val input = conn.inputStream
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            Log.e(TAG, "downloadImage: Failed to download image", e)
            return null
        }
    }

    @JvmStatic fun getPoster(file: String): Bitmap? {
        try {
            return downloadImage(getImageUrl(file))
        } catch (e: MalformedURLException) {
            Log.e(TAG, "getPoster: Invalid URL", e)
            return null
        }
    }

    @Throws(IOException::class)
    @JvmStatic private fun streamToString(stream: InputStream): String {
        val outStream = ByteArrayOutputStream()

        val buffer = ByteArray(1024)
        var length = stream.read(buffer)

        while (length != -1) {
            outStream.write(buffer, 0, length)
            length = stream.read(buffer)
        }

        return outStream.toString(ENCODING)
    }
}
