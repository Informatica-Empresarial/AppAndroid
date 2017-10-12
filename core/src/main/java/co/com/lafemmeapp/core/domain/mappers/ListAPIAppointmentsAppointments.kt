package co.com.lafemmeapp.core.domain.mappers

import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.dataprovider.network.entities.APIAppointment
import io.reactivex.functions.Function
import java.util.ArrayList


/**
 * Created by oscargallon on 5/22/17.
 */
class ListAPIAppointmentsAppointments : Function<List<APIAppointment>, List<Appointment>> {


    private object Holder {
        val instance = ListAPIAppointmentsAppointments()
    }

    companion object {
        val instace: ListAPIAppointmentsAppointments by lazy { Holder.instance }
    }


    override fun apply(t: List<APIAppointment>): List<Appointment> {
        return t.map { apiAppointment ->
            APIAppointmentAppointmentMapper.instace
                    .apply(apiAppointment)
        }
    }
}