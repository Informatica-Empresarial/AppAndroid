package co.com.lafemmeapp.lafemmeapp.presentation.profile

import co.com.lafemmeapp.core.domain.entities.abstracts.User
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks

/**
 * Created by oscar.gallon on 6/7/17.
 */
interface IProfileFragmentPresenter : IBaseFragmentPresenter, IFragmentCallbacks {

    var mProfileFragmentView: IProfileFragmentView

    fun getUserInfo(): User?

    fun updateUser(email: String, phoneNumber: String)
}