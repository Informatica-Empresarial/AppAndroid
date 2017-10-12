package co.com.lafemmeapp.lafemmeapp.presentation.history

import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView

/**
 * Created by oscargallon on 5/22/17.
 */
interface IHistoryFragmentView : IBaseFragmentView {

    fun showAppointments(appointments: List<Appointment>, areOngoing: Boolean)

    fun showProgressBar()

    fun hideProgressBar()

    fun changeLYSOSAvailabilityVisibility(visible: Boolean)

    fun checkOrUCheckSW(checked: Boolean)

    fun onGPSEnabled()

    fun onSessionChecked(isSpecialist: Boolean)

    fun onLocationPermissionsGranted()

    fun updateAppointment(appointment: Appointment)

    fun clearAppointments()


}