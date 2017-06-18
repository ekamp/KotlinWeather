package weather.ekamp.com.weatherappkotlin.model.networking

import android.app.Application
import okhttp3.Interceptor
import okhttp3.Response
import toothpick.Toothpick
import javax.inject.Inject

/**
 * Okhttp Interceptor used to add the Cache-Control header.
 * Currently caches for 10 min if network is available, 1 day if offline
 *
 * @since 1.0
 */
class CacheControlInterceptor constructor(application: Application): Interceptor {

    @Inject lateinit var networkStatusUtil : NetworkStatusUtil

    init {
        val scope = Toothpick.openScope(application)
        Toothpick.inject(this, scope)
    }

    override
    fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()

        if (request?.method().equals("GET")) {
            if (networkStatusUtil.internetConnectivityAvailable()) {
                request = request?.newBuilder()
                        ?.header("Cache-Control", "only-if-cached")
                        ?.build()
            } else {
                //Use stale cache thats at the most 1 day old if no network available
                request = request?.newBuilder()
                        ?.header("Cache-Control", "max-stale=86400")
                        ?.build()
            }
        }

        var originalResponse = chain?.proceed(request)
        //Cache results for 10 min
        return originalResponse?.newBuilder()
                ?.header("Cache-Control", "max-age=600")
                ?.build()
    }
}
