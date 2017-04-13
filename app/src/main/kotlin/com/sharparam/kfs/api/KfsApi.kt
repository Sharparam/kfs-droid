package com.sharparam.kfs.api

import android.net.Uri
import java.net.MalformedURLException
import java.net.URL

object KfsApi {
    private const val ENDPOINT = "http://kristinehamns-filmstudio.se/api.php"

    fun getRequestUrl(vararg params: Pair<String, Any>): URL {
        val builder = Uri.parse(ENDPOINT).buildUpon()
        for (param in params) {
            builder.appendQueryParameter(param.first, param.second.toString())
        }

        return URL(builder.build().toString())
    }

    @Throws(MalformedURLException::class)
    @JvmStatic fun getImageUrl(movie: Movie): URL {
        return getImageUrl(movie.image)
    }

    @Throws(MalformedURLException::class)
    @JvmStatic fun getImageUrl(image: String): URL {
        return URL("http", "kristinehamns-filmstudio.se", "/images/movies/" + image)
    }
}
