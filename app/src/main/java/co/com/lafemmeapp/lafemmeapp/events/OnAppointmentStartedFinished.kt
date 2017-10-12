package co.com.lafemmeapp.lafemmeapp.events

import co.com.lafemmeapp.core.domain.entities.Appointment

/**
 * Created by oscargallon on 6/12/17.
 */
data class OnAppointmentStartedFinished(val mStarted: Boolean,
                                        val mAppointment: Appointment)