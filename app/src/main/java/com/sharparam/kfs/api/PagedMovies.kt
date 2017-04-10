package com.sharparam.kfs.api

import org.json.JSONObject

class PagedMovies private constructor(private val movies: List<Movie>, val page: Int, val pages: Int, val size: Int,
                                      val count: Int) : Iterable<Movie> {
    override fun iterator(): Iterator<Movie> {
        return movies.iterator()
    }

    fun get(index: Int): Movie {
        return movies[index]
    }

    companion object {
        private const val DEFAULT_PAGE = 0

        private const val DEFAULT_PAGE_SIZE = 10

        @JvmStatic fun get(page: Int = DEFAULT_PAGE, size: Int = DEFAULT_PAGE_SIZE): PagedMovies {
            val json = KfsApi.request(Pair("mode", "archive"), Pair("page", page.toString()), Pair("size", size.toString()))
            return fromJson(JSONObject(json))
        }

        @JvmStatic private fun fromJson(obj: JSONObject): PagedMovies {
            val movies = Movie.fromJsonArray(obj.getJSONArray("movies"))
            val page = obj.getInt("page")
            val pages = obj.getInt("pages")
            val size = obj.getInt("size")
            val count = obj.getInt("count")
            return PagedMovies(movies, page, pages, size, count)
        }
    }
}
