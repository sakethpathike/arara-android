package com.sakethh.arara.unreleased

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.sakethh.arara.Constants
import com.sakethh.arara.MainActivity
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

@SuppressLint("StaticFieldLeak")
object UnreleasedCache{
    lateinit var okHttpClient:OkHttpClient
fun unreleasedCache(context:Context) {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val cache= Cache(File(context.cacheDir,"unreleased-cache"),cacheSize)
        okHttpClient= OkHttpClient.Builder()
            .cache(cache = cache)
            .addInterceptor {
                var request = it.request()
                request = if (isInternetAvailable(context)) { //online
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5)
                        .removeHeader("Pragma").build()
                } else { // offline
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).removeHeader("Pragma").build()
                }
                it.proceed(request)
            }
            .build()}
}
private fun isInternetAvailable(context:Context): Boolean {
    val isConnected: Boolean?
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    isConnected = activeNetwork != null && activeNetwork.isConnected
    return isConnected
}

