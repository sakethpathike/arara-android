package com.sakethh.arara.unreleased

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import okhttp3.*
import java.io.File
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
object UnreleasedCache {

    class OnlineInterceptor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val cacheControl = CacheControl.Builder().maxStale(1, TimeUnit.DAYS).build()
            request = request.newBuilder().cacheControl(cacheControl).removeHeader("Pragma")
                .build()
            return try{
                chain.proceed(request)
            }catch (_:Exception){ // java.net.SocketTimeoutException: timeout
                chain.proceed(request)
            }

        }
    }

    lateinit var okHttpClient: OkHttpClient
    fun unreleasedCache(context: Context) {
        class OfflineInterceptor() : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                if (!isInternetAvailable(context)) {
                    val maxStale = CacheControl.Builder().maxStale(7, TimeUnit.DAYS).build()
                    request =
                        request.newBuilder().cacheControl(maxStale).removeHeader("Pragma").build()
                }
                Log.d("AURORA LOG", "Triggered Offline Interceptor")
                return chain.proceed(request)
            }
        }

        val cacheSize = (10 * 1024 * 1024).toLong() // 20 MiB
        val cache = Cache(File(context.cacheDir, "unreleased-cache"), cacheSize)
        okHttpClient = OkHttpClient.Builder()
            .cache(cache = cache)
           .addInterceptor(
               if(isInternetAvailable(context = context)){
                   OnlineInterceptor()
               }else{
                   OfflineInterceptor()
               }
        ).build()
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val isConnected: Boolean?
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    isConnected = activeNetwork != null && activeNetwork.isConnected
    return isConnected
}
/*

{
    UnreleasedCache.request = it.request()
    if (!isInternetAvailable(context)) {
        UnreleasedCache.request = UnreleasedCache.request.newBuilder().header(
            "Cache-Control",
            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
        ).removeHeader("Pragma").build()
    }
    it.proceed(UnreleasedCache.request)
}.addNetworkInterceptor {
    UnreleasedCache.request = it.request()
    val cacheControl=CacheControl.Builder().maxAge(7,TimeUnit.DAYS).build() // cache's for 7 days
    UnreleasedCache.request = UnreleasedCache.request.newBuilder()
        .header("Cache-Control","public, max-age=$cacheControl" )
        .removeHeader("Pragma").build()
    it.proceed(UnreleasedCache.request)
}*/
