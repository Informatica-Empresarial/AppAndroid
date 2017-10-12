package co.com.lafemmeapp.core.domain.use_cases.appointments

import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.core.domain.entities.RateAppointmentParams
import co.com.lafemmeapp.core.domain.mappers.APIAppointmentAppointmentMapper
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.network.entities.RateAppointmentRequest
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by oscargallon on 6/12/17.
 */
class RateAppointmentUseCase(mSubsCribeOnScheduler: Scheduler,
                             mObserverOnScheduler: Scheduler)
    : UseCase<Appointment, RateAppointmentParams>(mSubsCribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: RateAppointmentParams): Observable<Appointment> {
        return mRepositoryFactory.sessionRepositoty
                .getSession()
                .flatMap { session ->
                    mRepositoryFactory.appointmentRepository
                            .rateAppointment("Bearer ${session.token}" ,
                                    params.appointmentUuid,
                                    RateAppointmentRequest(params.rating,
                                            params.comments,
                                            session.uuid))

                }.map { apiAppointment ->
            APIAppointmentAppointmentMapper.Companion
                    .instace.apply(apiAppointment)
        }
    }
}