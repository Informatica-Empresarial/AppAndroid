package co.com.lafemmeapp.dataprovider.network.entities

/**
 * Created by oscargallon on 6/12/17.
 */
data class RateAppointmentRequest(val rating: Int, val comments: String? = null,
                                  val userUuid: String) {
}