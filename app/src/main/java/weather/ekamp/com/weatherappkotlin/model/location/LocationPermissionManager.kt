package weather.ekamp.com.weatherappkotlin.model.location

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class LocationPermissionManager @Inject constructor(application: Application) {

    val application : Application = application

    companion object {
        val PERMISSIONS_REQUEST_LOCATION = 1095
    }

    /**
     * Checks whether or not the user has granted the location permission
     */
    open fun hasLocationPermissionBeenGranted(): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(application.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionCheck == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Prompts the user to grant this application the location permissions
     */
    fun requestLocationPermission(activity : Activity) {
        ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_LOCATION)
    }
}