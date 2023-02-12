package com.henrikhoang.themoviedb

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import retrofit2.Response

abstract class BaseRepository(
    private val context: Context
) {

    /**
     * Transform response into data resource
     */
    suspend fun <T> dataCall(
        block: suspend () -> Response<T>
    ): T {
        requireInternet()
        val response = block()
        return if (response.isSuccessful) {
            response.body() as T
        } else {
            throw ServerErrorException()
        }
    }

    private fun requireInternet() {
        if (!isInternetAvailable()) {
            throw NoInternetException()
        }
    }

    private fun isInternetAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkCapabilities = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}

class ServerErrorException: Exception()

class NoInternetException: Exception()

