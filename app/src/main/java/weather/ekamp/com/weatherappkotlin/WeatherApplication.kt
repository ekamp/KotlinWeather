package weather.ekamp.com.weatherappkotlin

import android.app.Application
import toothpick.smoothie.module.SmoothieApplicationModule
import toothpick.Toothpick
import weather.ekamp.com.weatherappkotlin.model.inject.WeatherApplicationModule

import toothpick.configuration.Configuration.forDevelopment
import toothpick.configuration.Configuration.forProduction
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator
import toothpick.Toothpick.setConfiguration

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val configuration = if (BuildConfig.DEBUG) forDevelopment() else forProduction()
        setConfiguration(configuration.disableReflection())
        FactoryRegistryLocator.setRootRegistry(FactoryRegistry())
        MemberInjectorRegistryLocator.setRootRegistry(MemberInjectorRegistry())

        val appScope = Toothpick.openScope(this)
        appScope.installModules(SmoothieApplicationModule(this), WeatherApplicationModule(this))
    }
}
