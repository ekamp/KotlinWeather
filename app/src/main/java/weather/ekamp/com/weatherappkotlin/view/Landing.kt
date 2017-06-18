package weather.ekamp.com.weatherappkotlin.view

import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import toothpick.Scope
import toothpick.Toothpick
import weather.ekamp.com.weatherappkotlin.R
import weather.ekamp.com.weatherappkotlin.model.location.UserLocationManager
import weather.ekamp.com.weatherappkotlin.model.parsers.WeatherDescription
import weather.ekamp.com.weatherappkotlin.presenter.LandingPresenter
import javax.inject.Inject

class Landing : AppCompatActivity(), LandingView {

    @Inject lateinit var presenter : LandingPresenter
    @Inject lateinit var userLocationManager : UserLocationManager
    lateinit private var errorDialog : ErrorDialog
    lateinit private var activityScope : Scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityScope = Toothpick.openScopes(application, this)
        Toothpick.inject(this, activityScope)
        setContentView(R.layout.activity_main)
        presenter.onAttachView(this)
    }

    override fun displayCurrentWeather(weatherDescription: WeatherDescription) {
        weather_description.text = weatherDescription.getWeatherInformation().description
        temperature.text = weatherDescription.main.temp
        var weatherIconPath = weatherDescription.getIconUrlPath()
        Picasso.with(this).
                load(weatherIconPath).
                fit().
                placeholder(R.drawable.cloud).
                error(R.drawable.cloud).
                into(weather_representation)
    }

    override fun displayUserLocation(userLocationForDisplay : String) {
        user_location.text = userLocationForDisplay
    }

    override fun displayLoadingIndicator() {
        loading_indicator.visibility = View.VISIBLE
        weather_description.visibility = View.GONE
        temperature.visibility = View.GONE
        weather_representation.visibility = View.GONE
    }

    override fun hideLoadingIndicator() {
        loading_indicator.visibility = View.GONE
        weather_description.visibility = View.VISIBLE
        temperature.visibility = View.VISIBLE
        weather_representation.visibility = View.VISIBLE
    }

    override fun displayErrorToUser(message: String) {
        errorDialog = ErrorDialog.newInstance(message)
        errorDialog.show(supportFragmentManager, "errorDialog")
    }

    override fun getUsersLocation() {
        userLocationManager.getLocationFromGoogleAPI(this, this)
    }

    override fun onLocationUpdated(location: Location) {
        presenter.onLocationCollected(location, userLocationManager.getCurrentAddress(location.latitude, location.longitude))
    }

    override fun onLocationNotFound() {
        //Notify the user that the location could not be collected
        displayErrorToUser("Location Could not be determined please try again")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroy()
    }
}
