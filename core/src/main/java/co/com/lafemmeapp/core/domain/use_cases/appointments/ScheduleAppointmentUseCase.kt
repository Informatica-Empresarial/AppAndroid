package co.com.lafemmeapp.core.domain.use_cases.appointments

import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.core.domain.mappers.APIAppointmentAppointmentMapper
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.exceptions.NoSessionException
import co.com.lafemmeapp.dataprovider.local.DBDataSource
import co.com.lafemmeapp.dataprovider.params.ScheduleAppointmentParams
import co.com.lafemmeapp.dataprovider.repo.RepositoryFactory
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by oscargallon on 5/23/17.
 */
class ScheduleAppointmentUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler)
    : UseCase<Appointment, ScheduleAppointmentParams>(subscribeOnScheduler, observerOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: ScheduleAppointmentParams): Observable<Appointment> {
        return mRepositoryFactory
                .valuesRepository.getValue(Constants.DBTOKEN_KEY)
                .flatMap { token ->
                    mRepositoryFactory
                            .appointmentRepository
                            .scheduleAppointment("Bearer $token", params.appointmentUuid,
                                    params.isSOS);
                }.map { apiAppointment ->
            APIAppointmentAppointmentMapper.instace
                    .apply(apiAppointment)
        }

    }
}