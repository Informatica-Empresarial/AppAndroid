package co.com.lafemmeapp.lafemmeapp.presentation.history_detail

import co.com.lafemmeapp.core.domain.entities.ViewService
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView

/**
 * Created by Stephys on 24/05/17.
 */
interface IHistoryDetailFragmentView : IBaseFragmentView {

    fun setDate(date: String)

    fun setTime(time: String)

    fun setServices(services: List<ViewService>)

    fun setAddress(address: String)

    fun setSpecialistNameOurCustomer(specialist: String)

    fun setPrice(price: String)

    fun setPhone(phone: String)

    fun showCancelButton(visibility: Int)

    fun onAppointmentCanceled()

    fun showHideRateAppointmentButton(visibility:Int)

    fun showHideShowAddress(visibility:Int)

    fun changeNameTitle(isSpecialist:Boolean)

    fun showHideBTNInitService(visibility: Int)

    fun showHideBTNFinishService(visibility: Int)


}