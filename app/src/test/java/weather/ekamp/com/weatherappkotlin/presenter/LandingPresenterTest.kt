package weather.ekamp.com.weatherappkotlin.presenter

import android.app.Application
import android.location.Location
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import toothpick.testing.ToothPickRule
import weather.ekamp.com.weatherappkotlin.model.networking.WeatherService
import weather.ekamp.com.weatherappkotlin.model.parsers.Weather
import weather.ekamp.com.weatherappkotlin.model.parsers.WeatherDescription
import weather.ekamp.com.weatherappkotlin.view.LandingView

class LandingPresenterTest {

    val toothpickRule = ToothPickRule(this)
        @Rule get

    private val TEST_MESSAGE = "Error"
    private val TEST_LOCATION_LAT_NUM = 40.758895
    private val TEST_LOCATION_LNG_NUM = -73.9873197

    @MockK
    internal lateinit var landingView: LandingView
    @MockK
    internal lateinit var weatherService: WeatherService
    @MockK
    internal lateinit var location: Location
    @MockK
    internal lateinit var application: Application
    @MockK
    internal lateinit var single: Single<WeatherDescription>
    @MockK
    internal lateinit var weatherDescription: WeatherDescription
    @MockK
    internal lateinit var throwable: Throwable

    @InjectMockKs
    lateinit var landingPresenter: LandingPresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        toothpickRule.setScopeName(application)
        toothpickRule.inject(this)
        landingPresenter.landingView = landingView
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
    }

    @Test
    fun test_onAttachView_collectsUsersLocation_and_displaysLoadingIndicator() {
        //GIVEN
        every { (landingView.getUsersLocation()) } returns Unit
        every { (landingView.displayLoadingIndicator()) } returns Unit

        //WHEN
        landingPresenter.onAttachView(landingView)

        //THEN
        verifySequence {
            landingView.displayLoadingIndicator()
            landingView.getUsersLocation()
        }
    }

    @Test
    fun test_createAndRegisterWeatherSubscription_createsSubscriptionToWeatherService() {
        //GIVEN
        every { (location.latitude) } returns (TEST_LOCATION_LAT_NUM)
        every { (location.longitude) } returns (TEST_LOCATION_LNG_NUM)
        val weatherArray: Array<Weather> = arrayOf(Weather("Rennes", "Soleil", "01d.png"))
        val temperatureInformation: WeatherDescription.TemperatureInformation = WeatherDescription.TemperatureInformation("15", "50", "10", "18")
        val single: Single<WeatherDescription> = Single.just(WeatherDescription(weatherArray, temperatureInformation))
        every { (weatherService.getWeatherInformation(TEST_LOCATION_LAT_NUM.toFloat().toString(), TEST_LOCATION_LNG_NUM.toFloat().toString())) } returns (single)

        //WHEN
        landingPresenter.createAndRegisterWeatherSubscription(location)

        //THEN
        verifySequence {
            weatherService.getWeatherInformation(any(), any())
        }
    }

    @Test
    fun test_createLocationSubscription_getsUserLocationFromLandingView() {
        //GIVEN
        every { (landingView.getUsersLocation()) } returns Unit

        //WHEN
        landingPresenter.createLocationSubscription()

        //THEN
        verifySequence {
            landingView.getUsersLocation()
        }
    }

    @Test
    fun test_onWeatherCollected_hidesProgressIndicatorAndShowsWeatherInformation() {
        //GIVEN
        every { (landingView.hideLoadingIndicator()) } returns Unit
        every { (landingView.displayCurrentWeather(weatherDescription)) } returns Unit

        //WHEN
        landingPresenter.onWeatherCollected(weatherDescription)

        //THEN
        verifySequence {
            landingView.hideLoadingIndicator()
            landingView.displayCurrentWeather(weatherDescription)
        }
    }

    @Test
    fun test_onWeatherCollectionFailure_hidesProgressIndicatorAndShowsError() {
        //GIVEN
        every { (landingView.hideLoadingIndicator()) } returns Unit
        every { (landingView.displayErrorToUser(TEST_MESSAGE)) } returns Unit
        every { (throwable.localizedMessage) } returns (TEST_MESSAGE)

        //WHEN
        landingPresenter.onWeatherCollectionFailure(throwable)

        //THEN
        verifySequence {
            landingView.hideLoadingIndicator()
            landingView.displayErrorToUser(TEST_MESSAGE)
        }
    }
}
