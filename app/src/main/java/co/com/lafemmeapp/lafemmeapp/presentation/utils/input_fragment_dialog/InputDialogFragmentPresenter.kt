package co.com.lafemmeapp.lafemmeapp.presentation.utils.input_fragment_dialog

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.InputType
import android.view.View
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.InputDialogFragmentResponse
import co.com.lafemmeapp.core.domain.entities.InputFragmentDialogParams
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks

/**
 * Created by oscargallon on 6/13/17.
 */
class InputDialogFragmentPresenter(val mInputDialogFragmentView: IInputDialogFragmentView)
    : IInputDialogFragmentPresenter {


    var mFragmentCallback: IFragmentCallbacks? = null

    var mInputDialogFragmentParams: InputFragmentDialogParams? = null

    override fun onCreateView(view: View) {
        mInputDialogFragmentView.initViewComponents(view)
        mInputDialogFragmentView.initComponents()
        mInputDialogFragmentParams.let {
            mInputDialogFragmentView.setDialogMessage(mInputDialogFragmentParams!!.message!!)
            mInputDialogFragmentView.setDialogTitle(mInputDialogFragmentParams!!.title!!)
            mInputDialogFragmentView.setInputFieldInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            mInputDialogFragmentView.setInputHint(mInputDialogFragmentParams!!.inputHint!!)
            if (!mInputDialogFragmentParams!!.showBothButtons) {
                mInputDialogFragmentView.hideNegativeButton()
            } else {
                mInputDialogFragmentView.setNegativeButtonText(mInputDialogFragmentParams!!.negativeButtonText!!)
            }

            mInputDialogFragmentView.setPositiveButtonText(mInputDialogFragmentParams!!.positiveButtonText!!)

        }

    }

    override fun onViewClicked(view: View?, parcelable: Parcelable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAttach(fragmentCallback: IFragmentCallbacks?) {
        mFragmentCallback = fragmentCallback
    }

    override fun onCreate(arguments: Bundle?) {
        arguments.let {
            mInputDialogFragmentParams = arguments!!.getParcelable("params")
        }
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

    override fun actionTriggered(view: View, wasPositive: Boolean, text: String) {
        mInputDialogFragmentParams.let {

            mFragmentCallback?.onViewClicked(view,
                    InputDialogFragmentResponse(mInputDialogFragmentParams!!.action!!,
                            wasPositive, text))

        }

    }
}