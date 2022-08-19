package com.sakethh.arara.unreleased

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.tooling.data.ContextCache
import com.sakethh.arara.MainActivity
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import kotlin.coroutines.coroutineContext

class UnreleasedOfflineCacheThing : Interceptor {
    @SuppressLint("ServiceCast")
    fun isInternetAvailable(context: Context): Boolean {
         var isConnected: Boolean = false // Initial Value
         val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
         if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
         return isConnected
         }
    private val cacheSize = (30 * 1024 * 1024).toLong() // 10 MB
    private val cacheObject = Cache(MainActivity().cacheDir, cacheSize)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        /*  if(!isInternetAvailable(Context)){
            val maxAvailability = 60 * 60 * 24 * 30 // cache available for 30days
            request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxAvailability")
                .removeHeader("Pragma")
                .build()
          }*/
        return chain.proceed(request)
}
    val okHttpClient = OkHttpClient.Builder()
        .cache(cacheObject)
      /*  .addInterceptor(UnreleasedOfflineCacheThing()) */ //offline
     .addNetworkInterceptor(UnreleasedOnlineCacheThing())
        .build()
}


class UnreleasedOnlineCacheThing: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val maxAvailability = 120 //seconds
        request.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAvailability")
            .removeHeader("Pragma")
            .build()
        return chain.proceed(request)
    }
}
