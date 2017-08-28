package weather.ekamp.com.weatherappkotlin.model.inject

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Provider

class CompositeDisposableProvider : Provider<CompositeDisposable> {
    override fun get(): CompositeDisposable {
        return CompositeDisposable()
    }
}
