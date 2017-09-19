package weather.ekamp.com.weatherappkotlin.model.networking

import android.app.Application
import weather.ekamp.com.weatherappkotlin.BuildConfig
import weather.ekamp.com.weatherappkotlin.model.parsers.WeatherDescription
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Collects weather information from OpenWeatherMap using Retrofit.
 */
@Singleton
open class WeatherService @Inject constructor(var application: Application) {

    private val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY
    private val TEMPERATURE_UNITS = "imperial"

    @Inject lateinit var weatherApi: WeatherApi

    open fun getWeatherInformation(lat: String?, long: String?): io.reactivex.Single<WeatherDescription> {
        return weatherApi.getCurrentWeather(lat, long, TEMPERATURE_UNITS, WEATHER_API_KEY)
    }
}
