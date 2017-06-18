package weather.ekamp.com.weatherappkotlin.model.location

import android.app.Activity
import android.content.ContentValues.TAG
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.location.LocationServices
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocationManager {

    @Inject lateinit var geocoder : Geocoder

    /**
     * Collects the user's location from the Google FusedLocation API and notifies the given
     * listener of this new location.
     */
    fun getLocationFromGoogleAPI(activity: Activity, locationUpdateListener: LocationUpdateListener) {
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationClient.lastLocation
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful && task.result != null) {
                    locationUpdateListener.onLocationUpdated(task.result)
                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)
                    locationUpdateListener.onLocationNotFound()
                }
            }
    }

    /**
     * Grabs the user's current location is the form of a displayable Address
     */
    fun getCurrentAddress(lat : Number, lon : Number) : Address {
        return geocoder.getFromLocation(lat.toDouble(), lon.toDouble(), 1)[0]
    }
}
