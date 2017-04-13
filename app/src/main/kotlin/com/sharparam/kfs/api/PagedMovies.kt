package com.sharparam.kfs.api

import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

data class PagedMovies(private val movies: List<Movie>, val page: Int, val pages: Int, val size: Int,
                       val count: Int) : Iterable<Movie> {
    override fun iterator(): Iterator<Movie> {
        return movies.iterator()
    }

    operator fun get(index: Int): Movie {
        return movies[index]
    }

    companion object {
        private const val DEFAULT_PAGE = 0

        private const val DEFAULT_PAGE_SIZE = 10

        fun generateRequest(page: Int = DEFAULT_PAGE, size: Int = DEFAULT_PAGE_SIZE, listener: (PagedMovies) -> Unit, errorListener: (VolleyError) -> Unit) = JsonObjectRequest(
            Request.Method.GET, KfsApi.getRequestUrl("mode" to "archive", "page" to page, "size" to size).toString(), null, {
            listener(fromJson(it))
        }, errorListener)

        private fun fromJson(obj: JSONObject): PagedMovies {
            val movies = Movie.fromJsonArray(obj.getJSONArray("movies"))
            return PagedMovies(movies, obj.getInt("page"), obj.getInt("pages"), obj.getInt("size"), obj.getInt("count"))
        }
    }
}
