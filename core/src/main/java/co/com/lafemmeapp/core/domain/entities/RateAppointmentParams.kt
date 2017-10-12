package co.com.lafemmeapp.core.domain.entities

/**
 * Created by oscargallon on 6/12/17.
 */
data class RateAppointmentParams(val rating: Int, val comments: String? = null,
                                 val appointmentUuid: String) {
}