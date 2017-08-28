package weather.ekamp.com.weatherappkotlin.model.networking

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

/**
 * Utility class used to provide network information.
 *
 * @since 1.0
 */
class NetworkStatusUtil {

    @Inject lateinit  var application : Application

    /**
     * Determines whether the device currently has an internet connection
     */
    fun internetConnectivityAvailable() : Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo.isConnectedOrConnecting
    }
}
