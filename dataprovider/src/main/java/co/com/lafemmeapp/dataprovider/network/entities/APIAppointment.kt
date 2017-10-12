package co.com.lafemmeapp.dataprovider.network.entities

/**
 * Created by oscargallon on 5/18/17.
 */
data class APIAppointment(val uuid: String, val status: String, val startDateTime: String,
                          val endDateTime: String, val location: APILocation,
                          val hasDisCountCoupon: Boolean, val currency: String,
                          val totalPrice: String,
                          val specialist: APISpecialistUser?,
                          val appointmentServices: List<APIAppointmentService>,
                          val customer: APICustomer?,
                          val discountCoupon: String,
                          val createdAt: String,
                          val updatedAt: String,
                          val appointmentRatings: List<APIAppointmentRating>? = null)