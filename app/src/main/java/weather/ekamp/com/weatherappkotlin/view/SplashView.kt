package weather.ekamp.com.weatherappkotlin.view

import android.app.Activity

/**
 * Splash or first View user will see when opening the Application
 */
interface SplashView {

    /**
     * Start the view / part of the application
     */
    fun startLanding()

    /**
     * Show the user rationale / error message dialog
     */
    fun showLocationPermissionRationaleDialog()

    /**
     * Gets reference to the Activity pertaining to this View.
     * Currently this is needed to determine User Location permission preferences
     */
    fun getActivityReference() : Activity
}
