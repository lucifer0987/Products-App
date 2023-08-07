package com.example.products_app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

interface NetworkConnectivity {
    fun getNetworkInfo(): NetworkInfo?
    fun isConnected(): Boolean
}

class Network(val context: Context) : NetworkConnectivity {
    override fun getNetworkInfo(): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    override fun isConnected(): Boolean {
        val info = getNetworkInfo()
        return info != null && info.isConnected
    }
}
