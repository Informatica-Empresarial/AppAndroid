package co.com.lafemmeapp.lafemmeapp.presentation.utils

import android.view.View
import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView

/**
 * Created by oscargallon on 6/10/17.
 */
interface IRateAppointmentDialogFragmentView : IBaseFragmentView, View.OnClickListener {
    fun changeStatus(status: String)

    fun onAppointmentRated(appointment: Appointment)

    fun showHideOptionsButtons(visibility: Int)

    fun showSpecialistAvatar(url: String)
}