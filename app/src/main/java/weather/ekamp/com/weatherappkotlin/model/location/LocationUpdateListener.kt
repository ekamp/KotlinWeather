package weather.ekamp.com.weatherappkotlin.model.location

import android.location.Location

interface LocationUpdateListener {
    /**
     * Invoked when the user's location has been found or has been updated
     *
     * @return The last known / collected location of the user
     */
    fun onLocationUpdated(location : Location)

    /**
     * Called when the location of the user could not be determined
     */
    fun onLocationNotFound()
}
