package co.com.lafemmeapp.lafemmeapp.presentation.profile

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView

/**
 * Created by oscar.gallon on 6/7/17.
 */
interface IProfileFragmentView : IBaseFragmentView {

    fun setName(haveToShow: Boolean, name: String?)

    fun setSpecialistName(haveToShow: Boolean, specialist: String?)

    fun setEmail(haveToShow: Boolean, email: String?)

    fun setPhone(haveToShow: Boolean, phone: String?)

    fun setCity(haveToShow: Boolean, city: String?)

    fun setAddress(haveToShow: Boolean, address: String?)

    fun setAvatar(haveToShow: Boolean,avatar: String?)

    fun showTerms(haveToShow: Boolean)

    fun showEmailError(s: String?)

    fun showPhoneError(s: String?)

    fun changeBTNSaveAvailability(disable: Boolean)

}