package weather.ekamp.com.weatherappkotlin.model.inject

import android.app.Application
import android.location.Geocoder
import javax.inject.Provider

class GeocoderProvider : Provider<Geocoder> {

    var application : Application

    constructor(application: Application) {
        this.application = application
    }

    override fun get(): Geocoder {
        return Geocoder(application)
    }
}
