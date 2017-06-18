package weather.ekamp.com.weatherappkotlin.view

import weather.ekamp.com.weatherappkotlin.model.location.LocationUpdateListener
import weather.ekamp.com.weatherappkotlin.model.parsers.WeatherDescription

interface LandingView : LocationUpdateListener {
    /**
     * Invoked when the current weather should be shown to the user
     */
    fun displayCurrentWeather(weatherDescription: WeatherDescription)

    /**
     * Invoked when the user's location should be displayed within the View
     *
     * @param addressForDisplay String representation of a users location
     */
    fun displayUserLocation(addressForDisplay : String)

    /**
     * Indicate the UI is not ready and we are waiting on a background service
     */
    fun displayLoadingIndicator()

    /**
     * Hide the loading indicator to signify the UI is ready to use
     */
    fun hideLoadingIndicator()

    /**
     * Called when the weather data collection encountered an exception
     */
    fun displayErrorToUser(message: String)

    /**
     * The View should be able to determine the users location
     */
    fun getUsersLocation()
}
