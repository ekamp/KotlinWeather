package weather.ekamp.com.weatherappkotlin.model.networking

import android.app.Application
import okhttp3.Cache
import weather.ekamp.com.weatherappkotlin.model.parsers.WeatherDescription
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Collects weather information from OpenWeatherMap using Retrofit.
 */
@Singleton
open class WeatherService @Inject constructor(var application: Application) {

    private val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    private val WEATHER_API_KEY = "662da23514137fcd303aa26e9c4cbbff"
    private val TEMPERATURE_UNITS = "imperial"
    private val sizeOfHttpCache : Long = 10 * 1024 * 1024 // 10 MiB
    lateinit var weatherApi : WeatherApi

    init {
        createRetrofit(application)
    }

    open fun getWeatherInformation(lat : String?, long : String?): io.reactivex.Single<WeatherDescription> {
        return weatherApi.getCurrentWeather(lat, long, TEMPERATURE_UNITS, WEATHER_API_KEY)
    }

    private fun createRetrofit(application : Application) {
        val clientBuilder = okhttp3.OkHttpClient.Builder()
        val httpCache = Cache(File(application.cacheDir, "http"), sizeOfHttpCache)
        clientBuilder.cache(httpCache)
        clientBuilder.addNetworkInterceptor(CacheControlInterceptor(application))

        val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addCallAdapterFactory(retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.create())
                .addConverterFactory(retrofit2.converter.moshi.MoshiConverterFactory.create())
                .build()
        weatherApi = retrofit.create(WeatherApi::class.java)
    }
}
