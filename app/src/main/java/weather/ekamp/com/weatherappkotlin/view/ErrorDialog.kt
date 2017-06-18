package weather.ekamp.com.weatherappkotlin.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import weather.ekamp.com.weatherappkotlin.R
import kotlinx.android.synthetic.main.error_dialog.*;

class ErrorDialog : DialogFragment() {

    lateinit private var message : String

    companion object {
        fun newInstance(message : String) : ErrorDialog {
            var newInstance = ErrorDialog()
            newInstance.message = message
            return newInstance
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.error_dialog, container)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        confirmationButton.setOnClickListener {
            dismiss()
        }

        errorMessage.text = "Something went wrong"
    }
}
