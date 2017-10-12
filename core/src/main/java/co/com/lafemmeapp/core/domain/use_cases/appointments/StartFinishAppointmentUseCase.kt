package co.com.lafemmeapp.core.domain.use_cases.appointments

import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.core.domain.mappers.APIAppointmentAppointmentMapper
import co.com.lafemmeapp.core.domain.params.StartFinishAppointmentParams
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by oscargallon on 6/12/17.
 */
class StartFinishAppointmentUseCase(mSubscribeOnScheduler: Scheduler,
                                    mObserverOnScheduler: Scheduler)
    : UseCase<Appointment, StartFinishAppointmentParams>(mSubscribeOnScheduler,
        mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: StartFinishAppointmentParams): Observable<Appointment> {
        return mRepositoryFactory.sessionRepositoty
                .getSession()
                .flatMap { session ->
                    if (params.start) {
                        mRepositoryFactory
                                .appointmentRepository
                                .startAppointment("Bearer ${session.token}",
                                        params.appointmentUuid)
                    } else {
                        mRepositoryFactory
                                .appointmentRepository
                                .finishAppointment("Bearer ${session.token}",
                                        params.appointmentUuid)
                    }

                }.map { apiAppointment ->
            APIAppointmentAppointmentMapper.instace
                    .apply(apiAppointment)
        }


    }
}