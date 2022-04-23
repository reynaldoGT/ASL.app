package com.neo.signLanguage.ui.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class NetworkViewModel(app: Application) : AndroidViewModel(app) {
    val connected = MutableLiveData<Boolean>()

    init {
        val manager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (manager != null &&
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        ) {
            val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
            manager.registerNetworkCallback(networkRequest, object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    connected.postValue(true)
                }

                override fun onLost(network: Network) {
                    connected.postValue(false)
                }

                override fun onUnavailable() {
                    connected.postValue(false)
                }
            })
        } else {
            connected.setValue(true)
        }
    }
}
