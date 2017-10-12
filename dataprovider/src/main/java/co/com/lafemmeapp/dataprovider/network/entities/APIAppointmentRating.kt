package co.com.lafemmeapp.dataprovider.network.entities


/**
 * Created by oscargallon on 6/12/17.
 */
data class APIAppointmentRating(val uuid: String, val rating: Int,
                                val comments: String? = null,
                                val appointmentUuid: String,
                                val userUuid: String,
                                val createdAt: String,
                                val updatedAt: String) {


}