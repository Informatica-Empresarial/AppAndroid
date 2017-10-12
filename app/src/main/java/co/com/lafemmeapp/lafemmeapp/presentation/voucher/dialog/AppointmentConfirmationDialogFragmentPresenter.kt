package co.com.lafemmeapp.lafemmeapp.presentation.voucher.dialog

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks

/**
 * Created by oscargallon on 5/23/17.
 */

class AppointmentConfirmationDialogFragmentPresenter
(val mAppointmentConfirmationDialogFragmentView: IAppointmentConfirmationDialogFragmentView) :
        IAppointmentConfirmationDialogFragmentPresenter {



    var mFragmentCallbacks: IFragmentCallbacks? = null
    var mSpecialistUser: SpecialistUser? = null

    override fun onCreateView(view: View?) {
        mAppointmentConfirmationDialogFragmentView.initViewComponents(view)
        mAppointmentConfirmationDialogFragmentView.initComponents()
        if (mSpecialistUser !== null) {
            mAppointmentConfirmationDialogFragmentView
                    .showSpecialistName("${mSpecialistUser!!.name} ${mSpecialistUser!!.lastName} " +
                            "pronto estara atendiendo tu servicio.")
            mAppointmentConfirmationDialogFragmentView.showSpecialistPhoneNumber("cel: ${mSpecialistUser!!.phoneNumber}")
            if (mSpecialistUser!!.avatar !== null) {
                mAppointmentConfirmationDialogFragmentView.showSpecialistAvatar(mSpecialistUser!!.avatar)
            }
        }
    }

    override fun onAttach(fragmentCallback: IFragmentCallbacks?) {
        mFragmentCallbacks = fragmentCallback
    }

    override fun onCreate(arguments: Bundle?) {
        if (arguments !== null) {
            mSpecialistUser = arguments.getParcelable(AppModuleConstants.SPECIALIST_USER)
        }
    }

    override fun onViewClicked(view: View?, parcelable: Parcelable?) {
        mFragmentCallbacks?.onViewClicked(view, parcelable)
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
