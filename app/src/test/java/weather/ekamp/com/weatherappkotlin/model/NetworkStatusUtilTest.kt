package weather.ekamp.com.weatherappkotlin.model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import org.easymock.EasyMock.expect
import org.easymock.EasyMockRule
import org.easymock.EasyMockSupport
import org.easymock.Mock
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import toothpick.testing.ToothPickRule
import weather.ekamp.com.weatherappkotlin.model.networking.NetworkStatusUtil
import javax.inject.Inject

class NetworkStatusUtilTest : EasyMockSupport() {

    var easyMockRule = EasyMockRule(this)
        @Rule get

    var toothpickRule = ToothPickRule(this)
        @Rule get

    @Mock internal lateinit var application : Application
    @Mock internal lateinit var connectivityManager : ConnectivityManager
    @Mock internal lateinit var networkInfo : NetworkInfo

    @Inject lateinit var networkStatusUtil : NetworkStatusUtil

    @Before
    fun setup() {
        toothpickRule.setScopeName(application)
        toothpickRule.inject(this)
    }

    @Test
    fun test_internetConnectivityAvailable_returnsTrue_when_networkInfoIsConnectedOrConnectingReturnsTrue() {
        //GIVEN
        expect(application.getSystemService(Context.CONNECTIVITY_SERVICE)).andReturn(connectivityManager)
        expect(connectivityManager.activeNetworkInfo).andReturn(networkInfo)
        expect(networkInfo.isConnectedOrConnecting).andReturn(true)
        replayAll()

        //WHEN
        val result = networkStatusUtil.internetConnectivityAvailable()

        //THEN
        verifyAll()
        assertThat(result, `is`(true))
    }
}
