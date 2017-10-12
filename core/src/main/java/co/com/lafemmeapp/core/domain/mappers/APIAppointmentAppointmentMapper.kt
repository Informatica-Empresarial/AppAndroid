package co.com.lafemmeapp.core.domain.mappers

import co.com.lafemmeapp.core.domain.entities.*
import co.com.lafemmeapp.dataprovider.network.entities.APIAppointment
import io.reactivex.functions.Function

/**
 * Created by oscargallon on 5/18/17.
 */
class APIAppointmentAppointmentMapper private constructor() : Function<APIAppointment, Appointment> {

    private object Holder {
        val instance = APIAppointmentAppointmentMapper()
    }

    companion object {
        val instace: APIAppointmentAppointmentMapper by lazy { Holder.instance }
    }

    override fun apply(apiAppointment: APIAppointment): Appointment {


        val appointmentService: List<AppointmentService> =
                apiAppointment.appointmentServices.map { apiAppointmentService ->
                    val service: Service? =
                            if (apiAppointmentService.service !== null)
                                APIServiceServiceMapper.getInstance()
                                        .apply(apiAppointmentService.service)
                            else null
                    AppointmentService(apiAppointmentService.appointmentUuid,
                            apiAppointmentService.serviceUuid,
                            apiAppointmentService.price,
                            apiAppointmentService.count,
                            service)
                }

        val appointmentRatings: List<AppointmentRating>? = if (apiAppointment.appointmentRatings !== null) {
            apiAppointment.appointmentRatings!!.map { rating ->
                AppointmentRating(rating.uuid, rating.rating,
                        rating.comments, rating.appointmentUuid,
                        rating.userUuid)
            }
        } else null

        val customer: Customer? = if (apiAppointment.customer !== null) APICustomerCustomerMapper.getInstance()
                .apply(apiAppointment.customer!!) else null

        val specialist: SpecialistUser? = if (apiAppointment.specialist !== null)
            APISpecialistUserSpecialistUser.getInstance()
                    .apply(apiAppointment.specialist!!) else null
        return Appointment(uuid = apiAppointment.uuid,
                status = apiAppointment.status, startDateTime = apiAppointment.startDateTime,
                endDateTime = apiAppointment.endDateTime,
                hasDisCountCoupon = apiAppointment.hasDisCountCoupon,
                currency = apiAppointment.currency,
                customer = customer,
                specialist = specialist,
                totalPrice = apiAppointment.totalPrice, appointmentServices = appointmentService,
                location = Location(apiAppointment.location.lat,
                        apiAppointment.location.lng,
                        apiAppointment.location.address),
                appointmentRatings = appointmentRatings)
    }
}