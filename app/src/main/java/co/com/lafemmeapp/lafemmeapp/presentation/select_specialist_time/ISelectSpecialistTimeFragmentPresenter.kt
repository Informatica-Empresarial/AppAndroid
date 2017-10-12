package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_time


import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

/**
 * Created by oscargallon on 5/9/17.
 */

interface ISelectSpecialistTimeFragmentPresenter : IBaseFragmentPresenter, DatePickerDialog.OnDateSetListener, IFragmentCallbacks {


    fun getSpecialistTime(date: Date)

    fun showCalendar()

}
