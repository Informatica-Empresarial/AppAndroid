package co.com.lafemmeapp.utilmodule.presentation.custom_progress_bar

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent

/**
 * Created by oscargallon on 5/25/17.
 */
class CustomProgressDialogPresenter(val mCustomProgressDialogView: ICustomProgressDialogView) : ICustomProgressDialogPresenter {


    override fun onCreate(arguments: Bundle?) {
        mCustomProgressDialogView.initViewComponents()
        mCustomProgressDialogView.initComponents()
        mCustomProgressDialogView.setColor()
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subscribeTextViewToTextWatcherEvent(textView: TextView, validatorMapper: ITextViewValidatorMapper) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChangeMappedEvent(textChangedMappedEvent: TextChangedMappedEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}