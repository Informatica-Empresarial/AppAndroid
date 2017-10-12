package co.com.lafemmeapp.core.domain.use_cases.appointments

import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest
import co.com.lafemmeapp.core.domain.mappers.APIAppointmentAppointmentMapper
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.exceptions.NoSessionException
import co.com.lafemmeapp.dataprovider.local.DBDataSource
import co.com.lafemmeapp.dataprovider.network.entities.AppointmentServiceRequest
import co.com.lafemmeapp.dataprovider.network.entities.CreateAppointmentRequest
import co.com.lafemmeapp.dataprovider.network.entities.LocationRequest
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import co.com.lafemmeapp.dataprovider.repo.RepositoryFactory
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


/**
 * Created by oscargallon on 5/18/17.
 */
class GetPreScheduleAppointmentUseCase(subscribeOnScheduler: Scheduler?, observerOnScheduler: Scheduler?)
    : UseCase<Appointment, ViewServicesRequest>(subscribeOnScheduler, observerOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH)

    init {
        mSimpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    }

    override fun buildUseCaseObservable(params: ViewServicesRequest): Observable<Appointment> {


        val dateJoda: DateTime = DateTime.parse(params.getmChooseDate(),
                DateTimeFormat.forPattern(if (params.getmChooseDate().length ==
                        "yyyy-MM-ddTHH:mm:ssZ".length || params
                        .getmChooseDate().length == "yyyy-MM-ddTHH:mm:ss".length) "yyyy-MM-dd'T'HH:mm:ss"
                else "yyyy-MM-dd'T'HH:mm")).toDateTime(DateTimeZone.UTC)

        params.setmChooseDate(mSimpleDateFormat.format(dateJoda.toDate()))


        return mRepositoryFactory.sessionRepositoty.getSession()
                .flatMap { sessionResponse ->
                    val appointmentServicesRequest: List<AppointmentServiceRequest>
                            = params.getmViewServicesSelected().map { serviceSelected ->
                        AppointmentServiceRequest(serviceSelected.uuid,
                                serviceSelected.quantity)
                    }

                    mRepositoryFactory
                            .appointmentRepository
                            .preScheduleAppointment("Bearer ${sessionResponse.token}",
                                    CreateAppointmentRequest(params.getmChooseDate(),
                                            sessionResponse.uuid, params.getmSpecialistUserSelected().uuid, false,
                                            LocationRequest(params.getmLatLng().latitude,
                                                    params.getmLatLng().longitude,
                                                    params.getmAddress()), null,
                                            appointmentServicesRequest))
                            .map { t -> APIAppointmentAppointmentMapper.instace.apply(t) }
                }


    }
}