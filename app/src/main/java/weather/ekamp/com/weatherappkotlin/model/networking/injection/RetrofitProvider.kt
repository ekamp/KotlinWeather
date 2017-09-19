package weather.ekamp.com.weatherappkotlin.model.networking.injection

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

/**
 * Created by anthonyfaucogney on 19/09/2017.
 */
class RetrofitProvider @Inject constructor(@Named("url") var url: String, @Named("okHttpClient") var okHttpClient: OkHttpClient) : Provider<Retrofit> {

    override fun get(): Retrofit {
        return retrofit2.Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.create())
                .addConverterFactory(retrofit2.converter.moshi.MoshiConverterFactory.create())
                .build()
    }
}