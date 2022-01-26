package com.graphybyte.githubtrendingrepo.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Real-time network connectivity status provider.
 * This helps to detect if internet connection is active or in-active.
 */
object ConnectivityProvider : ConnectivityManager.NetworkCallback() {

    private val networkLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    /**
     * Updates the value to be true when internet is available
     */
    override fun onAvailable(network: Network) {
        networkLiveData.postValue(true)
        super.onAvailable(network)
    }

    /**
     * Updates the value to be false when internet is not available
     */
    override fun onLost(network: Network) {
        networkLiveData.postValue(false)
        super.onLost(network)
    }

    /**
     * This function provides [LiveData] for network status.
     */
    fun networkLiveData(context: Context):Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(this)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), this)
        }

        /**
         * Loop through all the available network interfaces and check if any one interface has active internet.
         */
        var isNetworkConnected = false
        connectivityManager.allNetworks.forEach loop@{ network ->
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            networkCapabilities?.let { networkCapability ->
                if (networkCapability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isNetworkConnected = true
                    return@loop
                }
            }
        }

        return isNetworkConnected
    }

}