package weather.ekamp.com.weatherappkotlin.model.networking

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import weather.ekamp.com.weatherappkotlin.model.parsers.WeatherDescription

/**
 * Retrofit API used to collect weather forecast information from OpenWeatherMap
 *
 * @since 1.0
 */
interface WeatherApi {
    @GET("weather")
    fun getCurrentWeather(@Query("q") cityName : String?, @Query("units") tempUnitType : String?,
                          @Query("APPID") apiKey : String?) : Single<WeatherDescription>

    @GET("weather")
    fun getCurrentWeather(@Query("lat") lat : String?, @Query("lon") long : String?,
                          @Query("units") tempUnitType : String?,
                          @Query("APPID") apiKey : String?) : Single<WeatherDescription>

    @GET("forecast")
    fun getWeeklyWeather(@Query("q") cityName : String?, @Query("units") tempUnitType : String?,
                         @Query("APPID") apiKey : String?) : Observable<WeatherDescription>
}
