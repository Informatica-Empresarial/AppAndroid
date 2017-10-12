package co.com.lafemmeapp.lafemmeapp.presentation.utils.input_fragment_dialog

import android.view.View
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks

/**
 * Created by oscargallon on 6/13/17.
 */
interface IInputDialogFragmentPresenter:IBaseFragmentPresenter,IFragmentCallbacks{
    fun actionTriggered(view: View, wasPositive: Boolean, text: String)
}