package weather.ekamp.com.weatherappkotlin.presenter

import weather.ekamp.com.weatherappkotlin.model.location.LocationPermissionManager
import weather.ekamp.com.weatherappkotlin.view.SplashView
import javax.inject.Inject

class SplashPresenter @Inject constructor() {

    @Inject lateinit var locationPermissionManager : LocationPermissionManager

    lateinit var splashView : SplashView

    /**
     * Triggered when the View has been created or attached to a parent.
     */
    fun onAttachView(splashView : SplashView) {
        this.splashView = splashView
        if (checkIfStartupComplete()) {
            //Start Landing
            splashView.startLanding()
        }
    }

    /**
     * Determines if all tasks during start up are complete and ready to move to the next View.
     */
    fun checkIfStartupComplete(): Boolean {
        val hasLocationPermBeenGranted = locationPermissionManager.hasLocationPermissionBeenGranted()
        if (hasLocationPermBeenGranted) {
            return true
        }
        locationPermissionManager.requestLocationPermission(splashView.getActivityReference())
        return false
    }

    /**
     * Triggered when the user has granted the application Location permissions.
     */
    fun onLocationPermissionGranted() {
        splashView.startLanding()
    }

    /**
     * Triggered when the user has granted the application Location permissions.
     */
    fun onLocationPermissionDenied() {
        splashView.showLocationPermissionRationaleDialog()
    }
}
