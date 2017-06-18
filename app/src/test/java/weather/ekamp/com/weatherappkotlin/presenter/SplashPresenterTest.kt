package weather.ekamp.com.weatherappkotlin.presenter

import android.app.Activity
import android.app.Application
import org.easymock.*
import org.easymock.EasyMock.expect
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import toothpick.testing.ToothPickRule
import weather.ekamp.com.weatherappkotlin.model.location.LocationPermissionManager
import weather.ekamp.com.weatherappkotlin.view.SplashView
import javax.inject.Inject

import org.hamcrest.CoreMatchers.`is`

class SplashPresenterTest : EasyMockSupport() {

    val easyMockRule = EasyMockRule(this)
    @Rule get

    val toothpickRule = ToothPickRule(this)
    @Rule get

    @Mock lateinit var splashView : SplashView
    @Mock lateinit var activity : Activity
    @Mock lateinit var application : Application
    @Mock lateinit var locationPermissionManager : LocationPermissionManager

    @Inject lateinit var splashPresenter : SplashPresenter

    @Before
    fun setup() {
        toothpickRule.setScopeName(application)
        toothpickRule.inject(this)
        splashPresenter.splashView = splashView
    }

    @Test
    fun test_onAttachView_landingIsStarted_when_locationPermissionHasBeenGranted() {
        //GIVEN
        expect(splashView.startLanding())
        expect(locationPermissionManager.hasLocationPermissionBeenGranted()).andReturn(true)
        replayAll()

        //WHEN
        splashPresenter.onAttachView(splashView)

        //THEN
        verifyAll()
    }

    @Test
    fun test_onAttachView_locationPermissionIsRequested_when_locationPermissionHasNotBeenGranted() {
        //GIVEN
        expect(locationPermissionManager.hasLocationPermissionBeenGranted()).andReturn(false)
        expect(locationPermissionManager.requestLocationPermission(activity))
        expect(splashView.getActivityReference()).andReturn(activity)
        replayAll()

        //WHEN
        splashPresenter.onAttachView(splashView)

        //THEN
        verifyAll()
    }

    @Test
    fun test_checkIfStartupComplete_returns_true_when_locationPermissionHasBeenGranted() {
        //GIVEN
        expect(locationPermissionManager.hasLocationPermissionBeenGranted()).andReturn(true)
        replayAll()

        //WHEN
        val result = splashPresenter.checkIfStartupComplete()

        //THEN
        verifyAll()
        assertThat(result, `is`(true))
    }

    @Test
    fun test_checkIfStartupComplete_returns_false_when_locationPermissionHasNotBeenGranted() {
        //GIVEN
        expect(locationPermissionManager.hasLocationPermissionBeenGranted()).andReturn(false)
        expect(locationPermissionManager.requestLocationPermission(activity))
        expect(splashView.getActivityReference()).andReturn(activity)
        replayAll()

        //WHEN
        val result = splashPresenter.checkIfStartupComplete()

        //THEN
        verifyAll()
        assertThat(result, `is`(false))
    }

    @Test
    fun test_onLocationPermissionDenied_showsRationaleToUser() {
        //GIVEN
        expect(splashView.showLocationPermissionRationaleDialog())
        replayAll()

        //WHEN
        splashPresenter.onLocationPermissionDenied()

        //THEN
        verifyAll()
    }
}
