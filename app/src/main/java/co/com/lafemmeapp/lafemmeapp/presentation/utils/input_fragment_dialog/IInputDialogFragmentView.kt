package co.com.lafemmeapp.lafemmeapp.presentation.utils.input_fragment_dialog

import android.view.View
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView

/**
 * Created by oscargallon on 6/13/17.
 */
interface IInputDialogFragmentView:IBaseFragmentView, View.OnClickListener{

    fun setDialogTitle(text: String)

    fun setDialogMessage(text: String)

    fun setPositiveButtonText(text: String)

    fun setNegativeButtonText(text: String)

    fun hideNegativeButton()

    fun setInputHint(text: String)

    fun setInputFieldInputType(type: Int)

    fun showInputError(error: String)

    fun inputHasErrors(): Boolean

    fun changeBTNPositiveAvailability(enabled: Boolean)
}