package weather.ekamp.com.weatherappkotlin.model.inject

import android.app.Application
import android.location.Geocoder
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import toothpick.config.Module
import weather.ekamp.com.weatherappkotlin.model.networking.WeatherApi
import weather.ekamp.com.weatherappkotlin.model.networking.injection.HttpClientProvider
import weather.ekamp.com.weatherappkotlin.model.networking.injection.RetrofitProvider
import weather.ekamp.com.weatherappkotlin.model.networking.injection.WeatherApiProvider

class WeatherApplicationModule : Module {

    private val BASE_URL = "http://api.openweathermap.org/data/2.5/"

    constructor(application: Application) {
        bind(Geocoder::class.java).toProviderInstance(GeocoderProvider(application))
        bind(CompositeDisposable::class.java).toProviderInstance(CompositeDisposableProvider())
        bind(String::class.java).withName("url").toInstance(BASE_URL)
        bind(OkHttpClient::class.java).withName("okHttpClient").toProviderInstance(HttpClientProvider(application))
        bind(Retrofit::class.java).withName("retrofit").toProvider(RetrofitProvider::class.java)
        bind(WeatherApi::class.java).toProvider(WeatherApiProvider::class.java)
    }
}
