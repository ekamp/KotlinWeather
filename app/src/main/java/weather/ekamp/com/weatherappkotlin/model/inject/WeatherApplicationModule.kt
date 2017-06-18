package weather.ekamp.com.weatherappkotlin.model.inject

import android.app.Application
import android.location.Geocoder
import io.reactivex.disposables.CompositeDisposable
import toothpick.config.Module

class WeatherApplicationModule : Module {

    constructor(application : Application) {
        bind(Geocoder::class.java).toProviderInstance(GeocoderProvider(application))
        bind(CompositeDisposable::class.java).toProviderInstance(CompositeDisposableProvider())
    }
}
