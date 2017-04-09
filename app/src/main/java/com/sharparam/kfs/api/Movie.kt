package com.sharparam.kfs.api

import android.annotation.SuppressLint
import android.graphics.Bitmap
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

data class Movie(val id: Int, val title: String, val original: String, val genre: String, val country: String,
                 val director: String, val year: Int, val duration: Int, val imdb: String, val image: String,
                 val date: Date, val description: String, val rating: Double) : Serializable {
    val formattedDate: String
        get() {
            val format = SimpleDateFormat.getDateInstance()
            return format.format(date)
        }

    val formattedRating: String
        get() {
            val format = NumberFormat.getNumberInstance()
            format.minimumFractionDigits = 1
            format.maximumFractionDigits = 1
            return format.format(rating.toDouble())
        }

    val posterBitmap: Bitmap
        get() {
            return KfsApi.getPoster(image)
        }

    companion object {
        @JvmStatic fun findById(id: Int): Movie {
            val params = listOf(android.util.Pair("id", id.toString()))
            val json = KfsApi.request(params)
            return fromJsonObject(JSONObject(json))
        }

        @JvmStatic fun getActive(): List<Movie> {
            val json = KfsApi.request()
            return fromJsonArray(JSONArray(json))
        }

        @JvmStatic fun fromJsonArray(array: JSONArray): List<Movie> {
            return (0 until array.length()).asSequence().map { fromJsonObject(array.getJSONObject(it)) }.toList()
        }

        @JvmStatic private fun fromJsonObject(obj: JSONObject): Movie {
            @SuppressLint("SimpleDateFormat")
            val parser = SimpleDateFormat("yyyy-MM-dd")
            val date = parser.parse(obj.getString("date"))
            return Movie(obj.getInt("id"), obj.getString("title"), obj.getString("original"), obj.getString("genre"),
                    obj.getString("country"), obj.getString("director"), obj.getInt("year"), obj.getInt("duration"),
                    obj.getString("imdb"), obj.getString("image"), date, obj.getString("description"),
                    obj.getDouble("rating"))
        }
    }
}
