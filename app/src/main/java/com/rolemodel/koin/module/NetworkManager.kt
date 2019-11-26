package com.rolemodel.koin.module

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkManager(private val applicationContext: Context) {

    val isConnected: Boolean
        get() = applicationContext.isNetworkConnected(applicationContext)
}

@Suppress("DEPRECATION")
fun Context.isNetworkConnected(context: Context): Boolean {
    val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT < 23) {
        val networkInfo = connectivityManager.activeNetworkInfo

        networkInfo?.let {
            return networkInfo.isConnected
                    && (networkInfo.type == ConnectivityManager.TYPE_WIFI
                    || networkInfo.type == ConnectivityManager.TYPE_MOBILE)
        }
    } else {
        val network = connectivityManager.activeNetwork
        network?.let {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            networkCapabilities?.let {
                return it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        }
    }
    return false
}