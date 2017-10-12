package co.com.lafemmeapp.lafemmeapp.presentation.voucher.dialog

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView

/**
 * Created by oscargallon on 5/23/17.
 */
interface IAppointmentConfirmationDialogFragmentView : IBaseFragmentView {

    fun showSpecialistAvatar(avatarUrl: String)

    fun showSpecialistName(name: String)

    fun showSpecialistPhoneNumber(phoneNumber: String)
}