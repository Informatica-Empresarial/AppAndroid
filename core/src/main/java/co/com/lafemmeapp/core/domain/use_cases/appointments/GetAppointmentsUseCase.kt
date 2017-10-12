package co.com.lafemmeapp.core.domain.use_cases.appointments

import co.com.lafemmeapp.core.domain.Constants
import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.core.domain.mappers.ListAPIAppointmentsAppointments
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by oscargallon on 5/22/17.
 */
class GetAppointmentsUseCase(subscribeOnScheduler: Scheduler?, observerOnScheduler: Scheduler?) :
        UseCase<List<Appointment>, Void>(subscribeOnScheduler, observerOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: Void?): Observable<List<Appointment>> {
        return mRepositoryFactory.sessionRepositoty
                .getSession()
                .flatMap { sessionResult ->
                    mRepositoryFactory
                            .appointmentRepository
                            .getAppointments("Bearer ${sessionResult
                                    .token}", sessionResult.uuid,
                                    sessionResult.roles!==null
                            && sessionResult.roles.contains(
                                            Constants.SPECIALIST_ROLE))
                }.map { list -> ListAPIAppointmentsAppointments.instace.apply(list) }
    }
}