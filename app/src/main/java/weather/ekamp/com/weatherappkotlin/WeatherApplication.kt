package weather.ekamp.com.weatherappkotlin

import android.app.Application
import toothpick.Toothpick
import toothpick.smoothie.module.SmoothieApplicationModule
import weather.ekamp.com.weatherappkotlin.model.inject.WeatherApplicationModule

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val appScope = Toothpick.openScope(this)
        appScope.installModules(SmoothieApplicationModule(this), WeatherApplicationModule(this))
    }
}
