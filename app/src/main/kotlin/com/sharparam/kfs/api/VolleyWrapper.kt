package com.sharparam.kfs.api

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class VolleyWrapper(ctx: Context) {
    val requestQueue: RequestQueue = Volley.newRequestQueue(ctx.applicationContext)

    val imageLoader = ImageLoader(requestQueue, object : ImageLoader.ImageCache {
        private val cache = LruCache<String, Bitmap>(20)

        override fun getBitmap(url: String?) = cache.get(url)

        override fun putBitmap(url: String?, bitmap: Bitmap?) {
            cache.put(url, bitmap)
        }
    })

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }

    companion object {
        private var instance: VolleyWrapper? = null

        @JvmStatic
        fun getInstance(ctx: Context): VolleyWrapper {
            if (instance == null)
                instance = VolleyWrapper(ctx)
            return instance as VolleyWrapper
        }
    }
}
