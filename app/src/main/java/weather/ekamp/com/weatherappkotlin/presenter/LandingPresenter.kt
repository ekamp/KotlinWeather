package weather.ekamp.com.weatherappkotlin.presenter

import android.location.Address
import android.location.Location
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import weather.ekamp.com.weatherappkotlin.view.LandingView
import weather.ekamp.com.weatherappkotlin.model.networking.WeatherService
import weather.ekamp.com.weatherappkotlin.model.parsers.WeatherDescription
import javax.inject.Inject

/**
 * Presenter responsible for handling a LandingView.
 */
class LandingPresenter @Inject constructor() {

    @Inject lateinit var weatherService : WeatherService

    lateinit var landingView: LandingView
    val disposableSubscriptions : CompositeDisposable = CompositeDisposable()

    /**
     * Triggered by the View when the view has been created or attached to a parent.
     */
    fun onAttachView(landingView: LandingView) {
        this.landingView = landingView
        createLocationSubscription()
        landingView.displayLoadingIndicator()
    }

    /**
     * Triggered when the location has been determined.
     */
    fun onLocationCollected(location: Location, address: Address) {
        createAndRegisterWeatherSubscription(location)
        address.locality?.let { addressForDisplay ->
            landingView.displayUserLocation(addressForDisplay)
        }
    }

    /**
     * Creates and sets up the proper Rx Subscriptions to collect whether information and pass it
     * to the View.
     */
    fun createAndRegisterWeatherSubscription(location: Location) {
        val latitude = location.latitude.toFloat().toString()
        val longitude = location.longitude.toFloat().toString()
        val weatherSubscription = weatherService.getWeatherInformation(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onWeatherCollected,
                        this::onWeatherCollectionFailure)
        disposableSubscriptions.add(weatherSubscription)
    }

    /**
     * Creates a subscription to location services.
     */
    fun createLocationSubscription() {
        landingView.getUsersLocation()
    }

    /**
     * Cleanup function called when the View is being destroyed
     */
    fun onViewDestroy() {
        disposableSubscriptions.dispose()
    }

    /**
     * Triggered when weather information has been collected.
     */
    fun onWeatherCollected(weatherDescription: WeatherDescription) {
        landingView.hideLoadingIndicator()
        landingView.displayCurrentWeather(weatherDescription)
    }

    /**
     * Triggered when the weather information could not be collected
     */
    fun onWeatherCollectionFailure(throwable: Throwable) {
        landingView.hideLoadingIndicator()
        landingView.displayErrorToUser(throwable.localizedMessage)
    }
}
