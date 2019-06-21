package weather.ekamp.com.weatherappkotlin.model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import toothpick.testing.ToothPickRule
import weather.ekamp.com.weatherappkotlin.model.networking.NetworkStatusUtil

class NetworkStatusUtilTest {

    val toothpickRule = ToothPickRule(this)
        @Rule get

    @MockK
    internal lateinit var application: Application
    @MockK
    internal lateinit var connectivityManager: ConnectivityManager
    @MockK
    internal lateinit var networkInfo: NetworkInfo

    @InjectMockKs
    lateinit var networkStatusUtil: NetworkStatusUtil

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        toothpickRule.setScopeName(application)
        toothpickRule.inject(this)
    }

    @Test
    fun test_internetConnectivityAvailable_returnsTrue_when_networkInfoIsConnectedOrConnectingReturnsTrue() {
        //GIVEN
        every { application.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetworkInfo } returns networkInfo
        every { networkInfo.isConnectedOrConnecting } returns true

        //WHEN
        val result = networkStatusUtil.internetConnectivityAvailable()

        //THEN
        assertThat(result, `is`(true))
    }
}
