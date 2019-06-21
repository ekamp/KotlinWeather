package weather.ekamp.com.weatherappkotlin.presenter

import android.app.Activity
import android.app.Application
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import toothpick.testing.ToothPickRule
import weather.ekamp.com.weatherappkotlin.model.location.LocationPermissionManager
import weather.ekamp.com.weatherappkotlin.view.SplashView

class SplashPresenterTest {

    val toothpickRule = ToothPickRule(this)
        @Rule get

    @MockK
    lateinit var splashView: SplashView
    @MockK
    lateinit var activity: Activity
    @MockK
    lateinit var application: Application
    @MockK
    lateinit var locationPermissionManager: LocationPermissionManager

    @InjectMockKs
    lateinit var splashPresenter: SplashPresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        toothpickRule.setScopeName(application)
        toothpickRule.inject(this)
        splashPresenter.splashView = splashView
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
    }

    @Test
    fun test_onAttachView_landingIsStarted_when_locationPermissionHasBeenGranted() {
        //GIVEN
        every { splashView.startLanding() } returns Unit
        every { locationPermissionManager.hasLocationPermissionBeenGranted() } returns true

        //WHEN
        splashPresenter.onAttachView(splashView)

        //THEN
        verifySequence {
            splashView.startLanding()
        }
    }

    @Test
    fun test_onAttachView_locationPermissionIsRequested_when_locationPermissionHasNotBeenGranted() {
        //GIVEN
        every { locationPermissionManager.hasLocationPermissionBeenGranted() } returns false
        every { locationPermissionManager.requestLocationPermission(activity) } returns Unit
        every { splashView.getActivityReference() } returns activity

        //WHEN
        splashPresenter.onAttachView(splashView)

        //THEN
        verifySequence {
            splashView.getActivityReference()
        }
    }

    @Test
    fun test_checkIfStartupComplete_returns_true_when_locationPermissionHasBeenGranted() {
        //GIVEN
        every { locationPermissionManager.hasLocationPermissionBeenGranted() } returns true

        //WHEN
        val result = splashPresenter.checkIfStartupComplete()

        //THEN
        assertThat(result, `is`(true))
    }

    @Test
    fun test_checkIfStartupComplete_returns_false_when_locationPermissionHasNotBeenGranted() {
        //GIVEN
        every { locationPermissionManager.hasLocationPermissionBeenGranted() } returns false
        every { locationPermissionManager.requestLocationPermission(activity) } returns Unit
        every { splashView.getActivityReference() } returns activity

        //WHEN
        val result = splashPresenter.checkIfStartupComplete()

        //THEN
        assertThat(result, `is`(false))
    }

    @Test
    fun test_onLocationPermissionDenied_showsRationaleToUser() {
        //GIVEN
        every { splashView.showLocationPermissionRationaleDialog() } returns Unit

        //WHEN
        splashPresenter.onLocationPermissionDenied()

        //THEN
        verifySequence {
            splashView.showLocationPermissionRationaleDialog()
        }
    }
}
