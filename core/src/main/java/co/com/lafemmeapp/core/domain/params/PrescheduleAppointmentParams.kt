package co.com.lafemmeapp.core.domain.params

import co.com.lafemmeapp.core.domain.entities.Location
import co.com.lafemmeapp.dataprovider.network.entities.AppointmentServiceRequest

/**
 * Created by oscargallon on 5/18/17.
 */
class PrescheduleAppointmentParams(val startDateTime: String, val customerUuid: String,
                                   val specialistUuid: String, val hasDiscountCoupon: Boolean,
                                   val location: Location,
                                   val discountCupon: String,
                                   val appointmentServices: List<AppointmentServiceRequest>)