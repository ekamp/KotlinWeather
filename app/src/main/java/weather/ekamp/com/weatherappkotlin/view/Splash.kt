package weather.ekamp.com.weatherappkotlin.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import toothpick.Scope
import toothpick.Toothpick
import weather.ekamp.com.weatherappkotlin.R
import weather.ekamp.com.weatherappkotlin.model.location.LocationPermissionManager
import weather.ekamp.com.weatherappkotlin.presenter.SplashPresenter
import javax.inject.Inject

class Splash : AppCompatActivity(), SplashView {
    val ERROR_DIALOG_TAG = "location_permission_error_dialog"

    @Inject
    lateinit var presenter: SplashPresenter
    lateinit var activityScope: Scope

    lateinit var errorDialog: ErrorDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityScope = Toothpick.openScopes(application, this)
        Toothpick.inject(this, activityScope)
        setContentView(R.layout.activity_splash)
        presenter.onAttachView(this)
    }

    override fun getActivityReference(): Activity {
        return this
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LocationPermissionManager.PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.onLocationPermissionGranted()
            } else {
                presenter.onLocationPermissionDenied()
            }
        }
    }

    override fun startLanding() {
        val activityIntent = Intent(this, Landing::class.java)
        activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(activityIntent)
    }

    override fun showLocationPermissionRationaleDialog() {
        errorDialog = ErrorDialog.newInstance(getString(R.string.location_permission_rationale_message))
        errorDialog.show(supportFragmentManager, ERROR_DIALOG_TAG)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (errorDialog.isVisible) {
            errorDialog.dismiss()
        }
    }
}
