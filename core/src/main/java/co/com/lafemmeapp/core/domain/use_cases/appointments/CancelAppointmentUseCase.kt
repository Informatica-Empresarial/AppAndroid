package co.com.lafemmeapp.core.domain.use_cases.appointments

import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.core.domain.mappers.APIAppointmentAppointmentMapper
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by Stephys on 25/05/17.
 */
class CancelAppointmentUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler)
    : UseCase<Appointment, String>(subscribeOnScheduler, observerOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(appointmentUuid: String): Observable<Appointment> {
        return mRepositoryFactory.valuesRepository
                .getValue(Constants.DBTOKEN_KEY)
                .flatMap { token ->
                    mRepositoryFactory.appointmentRepository
                            .cancelAppointment("Bearer $token", appointmentUuid);
                }.map { apiAppointment ->
            APIAppointmentAppointmentMapper.instace
                    .apply(apiAppointment)
        }

    }
}