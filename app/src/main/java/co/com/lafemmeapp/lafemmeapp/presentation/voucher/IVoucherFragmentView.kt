package co.com.lafemmeapp.lafemmeapp.presentation.voucher

import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.core.domain.entities.ViewService
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView

/**
 * Created by oscargallon on 5/17/17.
 */
interface IVoucherFragmentView : IBaseFragmentView {

    fun showVoucher()

    fun showServices(services: List<ViewService>)

    fun showAppointmentAddress(address: String)

    fun showAppointmentSpecialist(name: String, lastName: String)

    fun showProgressBar()

    fun dissmissProgressBar()

    fun showAppointmentPrice(price: Int?)

    fun showAppointmentDate(date: String)

    fun showAppointmentTime(time: String)

    fun changeBTNNextAvailability(enabled: Boolean)

    fun showConfirmationAppointmentDialog(specialistUser: SpecialistUser)




}