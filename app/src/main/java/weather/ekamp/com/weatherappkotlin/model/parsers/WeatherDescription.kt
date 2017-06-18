package weather.ekamp.com.weatherappkotlin.model.parsers
open class WeatherDescription(val weather: Array<Weather>, val main : TemperatureInformation) {

    fun getWeatherInformation() : Weather {
        return weather[0]
    }

    fun getIconUrlPath(): String {
        var iconPath = getWeatherInformation().icon
        return "https://openweathermap.org/img/w/$iconPath.png"
    }

    class TemperatureInformation(val temp : String, val humidity : String, val temp_min : String, val temp_max : String)
}
