package weather.ekamp.com.weatherappkotlin.model.networking.injection

import retrofit2.Retrofit
import weather.ekamp.com.weatherappkotlin.model.networking.WeatherApi
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

/**
 * Created by anthonyfaucogney on 19/09/2017.
 */
class WeatherApiProvider @Inject constructor(@Named("retrofit") var retrofit: Retrofit) : Provider<WeatherApi> {

    override fun get(): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }
}