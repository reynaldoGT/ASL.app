package com.neo.signLanguage.utils
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity


class NetworkStateReceiver (var activity: AppCompatActivity) {
    fun isOnline(): Boolean {
        // Checking internet connectivity
        val connectivityMgr =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        var activeNetwork: NetworkInfo? = null
        if (connectivityMgr != null) {
            activeNetwork = connectivityMgr.activeNetworkInfo
        }
        return activeNetwork != null
    }
}