package weather.ekamp.com.weatherappkotlin.model.networking.injection

import android.app.Application
import okhttp3.Cache
import okhttp3.OkHttpClient
import weather.ekamp.com.weatherappkotlin.model.networking.CacheControlInterceptor
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

/**
 * Created by anthonyfaucogney on 19/09/2017.
 */
class HttpClientProvider @Inject constructor(@Named("application") var application: Application) : Provider<OkHttpClient> {

    companion object {
        private val sizeOfHttpCache: Long = 10 * 1024 * 1024 // 10 MiB
    }

    override fun get(): OkHttpClient {
        val clientBuilder = okhttp3.OkHttpClient.Builder()
        val httpCache = Cache(File(application.cacheDir, "http"), sizeOfHttpCache)
        clientBuilder.cache(httpCache)
        clientBuilder.addNetworkInterceptor(CacheControlInterceptor(application))
        return clientBuilder.build()
    }
}