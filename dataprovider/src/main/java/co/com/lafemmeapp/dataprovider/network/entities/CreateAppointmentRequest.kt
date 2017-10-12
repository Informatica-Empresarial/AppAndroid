package co.com.lafemmeapp.dataprovider.network.entities


/**
 * Created by oscargallon on 5/18/17.
 */
data class CreateAppointmentRequest(val startDateTime: String, val customerUuid: String,
                               val specialistUuid: String, val hasDiscountCoupon: Boolean? = false,
                               val location: LocationRequest,
                               val discountCupon: String? = null, val appointmentServices: List<AppointmentServiceRequest>)



