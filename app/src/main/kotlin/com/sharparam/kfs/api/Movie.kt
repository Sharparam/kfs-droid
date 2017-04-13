package com.sharparam.kfs.api

import android.annotation.SuppressLint
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable
import java.net.URL
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

data class Movie(val id: Int, val title: String, val original: String, val genre: String, val country: String,
                 val director: String, val year: Int, val duration: Int, val imdb: String, val image: String,
                 val date: Date, val description: String, val rating: Double) : Serializable {
    val formattedDate: String = SimpleDateFormat.getDateInstance().format(date)

    val formattedRating: String = NumberFormat.getNumberInstance().apply {
        minimumFractionDigits = 1
        maximumFractionDigits = 1
    }.format(rating)

    val posterUrl = KfsApi.getImageUrl(this)

    companion object {
        fun generateRequest(listener: (List<Movie>) -> Unit, errorListener: (VolleyError) -> Unit) = JsonArrayRequest(
            Request.Method.GET, KfsApi.getRequestUrl().toString(), null, { listener(fromJsonArray(it)) }, errorListener
        )

        fun generateRequest(id: Int, listener: (Movie) -> Unit, errorListener: (VolleyError) -> Unit) = JsonObjectRequest(
            Request.Method.GET, generateUrl(id).toString(), null, { listener(fromJsonObject(it)) }, errorListener)

        internal fun fromJsonArray(array: JSONArray): List<Movie> {
            return (0 until array.length()).asSequence().map { fromJsonObject(array.getJSONObject(it)) }.toList()
        }

        @SuppressLint("SimpleDateFormat")
        private fun fromJsonObject(obj: JSONObject): Movie {
            val date = SimpleDateFormat("yyyy-MM-dd").parse(obj.getString("date"))
            return Movie(obj.getInt("id"), obj.getString("title"), obj.getString("original"), obj.getString("genre"),
                obj.getString("country"), obj.getString("director"), obj.getInt("year"), obj.getInt("duration"),
                obj.getString("imdb"), obj.getString("image"), date, obj.getString("description"),
                obj.getDouble("rating"))
        }

        private fun generateUrl(id: Int): URL = KfsApi.getRequestUrl("id" to id)
    }
}
