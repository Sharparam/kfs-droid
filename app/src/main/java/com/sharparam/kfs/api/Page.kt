package com.sharparam.kfs.api

import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

data class Page(val id: Int, val name: String, val title: String, val content: String,
                val sort: Int, val isEnabled: Boolean) {
    companion object {
        @JvmStatic
        @Throws(IOException::class, JSONException::class)
        fun findByName(name: String): Page = fromJsonObject(JSONObject(KfsApi.request(Pair("page", name))))

        @JvmStatic
        fun generateErrorPage(e: Throwable): Page = Page(0, "error", "Error",
            "ERROR: " + e.message, 0, true)

        @Throws(JSONException::class)
        private fun fromJsonObject(obj: JSONObject): Page = Page(obj.getInt("id"),
            obj.getString("name"), obj.getString("title"), obj.getString("content"),
            obj.getInt("sort"), obj.getBoolean("enabled"))
    }
}
