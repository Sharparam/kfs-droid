package com.sharparam.kfs.api

import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

data class Page(val id: Int, val name: String, val title: String, val content: String,
                val sort: Int, val isEnabled: Boolean) {
    companion object {
        fun generateRequest(name: String, listener: (Page) -> Unit, errorListener: (VolleyError) -> Unit = {
            listener(generateErrorPage(it.cause ?: Exception(it.message)))
        }) = JsonObjectRequest(Request.Method.GET, generateUrl(name).toString(), null, {
            listener(fromJsonObject(it))
        }, errorListener)

        @JvmStatic
        fun generateErrorPage(e: Throwable): Page = Page(0, "error", "Error",
            "ERROR: " + e.message, 0, true)

        @Throws(JSONException::class)
        private fun fromJsonObject(obj: JSONObject): Page = Page(obj.getInt("id"),
            obj.getString("name"), obj.getString("title"), obj.getString("content"),
            obj.getInt("sort"), obj.getBoolean("enabled"))

        private fun generateUrl(name: String): URL = KfsApi.getRequestUrl("page" to name)
    }
}
