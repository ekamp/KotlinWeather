package weather.ekamp.com.weatherappkotlin.presenter

import android.app.Application
import android.location.Location
import io.reactivex.Single
import org.easymock.*
import org.easymock.EasyMock.expect
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import toothpick.testing.ToothPickRule
import weather.ekamp.com.weatherappkotlin.model.networking.WeatherService
import weather.ekamp.com.weatherappkotlin.model.parsers.WeatherDescription
import weather.ekamp.com.weatherappkotlin.view.LandingView
import javax.inject.Inject

class LandingPresenterTest : EasyMockSupport() {

    var easyMockRule = EasyMockRule(this)
    @Rule get

    var toothpickRule = ToothPickRule(this)
    @Rule get

    private val TEST_MESSAGE = "Error"
    private val TEST_LOCATION_LAT_NUM = 40.758895
    private val TEST_LOCATION_LNG_NUM = -73.9873197

    @Mock internal lateinit var landingView : LandingView
    @Mock internal lateinit var weatherService : WeatherService
    @Mock internal lateinit var location : Location
    @Mock internal lateinit var application : Application
    @Mock internal lateinit var single : Single<WeatherDescription>
    @Mock internal lateinit var weatherDescription : WeatherDescription
    @Mock internal lateinit var throwable : Throwable

    @Inject lateinit var landingPresenter : LandingPresenter

    @Before
    fun setup() {
        toothpickRule.setScopeName(application)
        toothpickRule.inject(this)
        landingPresenter.landingView = landingView
    }

    @Test
    fun test_onAttachView_collectsUsersLocation_and_displaysLoadingIndicator() {
        //GIVEN
        expect(landingView.getUsersLocation())
        expect(landingView.displayLoadingIndicator())
        replayAll()

        //WHEN
        landingPresenter.onAttachView(landingView)

        //THEN
        verifyAll()
    }

    @Test
    fun test_createAndRegisterWeatherSubscription_createsSubscriptionToWeatherService() {
        //GIVEN
        expect(location.latitude).andReturn(TEST_LOCATION_LAT_NUM)
        expect(location.longitude).andReturn(TEST_LOCATION_LNG_NUM)
        expect(weatherService.getWeatherInformation(TEST_LOCATION_LAT_NUM.toFloat().toString(), TEST_LOCATION_LNG_NUM.toFloat().toString())).andReturn(single)
        replayAll()

        //WHEN
        landingPresenter.createAndRegisterWeatherSubscription(location)

        //THEN
        verifyAll()
    }

    @Test
    fun test_createLocationSubscription_getsUserLocationFromLandingView() {
        //GIVEN
        expect(landingView.getUsersLocation())
        replayAll()

        //WHEN
        landingPresenter.createLocationSubscription()

        //THEN
        verifyAll()
    }

    @Test
    fun test_onWeatherCollected_hidesProgressIndicatorAndShowsWeatherInformation() {
        //GIVEN
        expect(landingView.hideLoadingIndicator())
        expect(landingView.displayCurrentWeather(weatherDescription))
        replayAll()

        //WHEN
        landingPresenter.onWeatherCollected(weatherDescription)

        //THEN
        verifyAll()
    }

    @Test
    fun test_onWeatherCollectionFailure_hidesProgressIndicatorAndShowsError() {
        //GIVEN
        expect(landingView.hideLoadingIndicator())
        expect(landingView.displayErrorToUser(TEST_MESSAGE))
        expect(throwable.localizedMessage).andReturn(TEST_MESSAGE)
        replayAll()

        //WHEN
        landingPresenter.onWeatherCollectionFailure(throwable)

        //THEN
        verifyAll()
    }
}
