package co.com.lafemmeapp.core.domain.use_cases.appointments

import co.com.lafemmeapp.core.domain.entities.AppointmentAvailability
import co.com.lafemmeapp.core.domain.mappers.APIAppointmentAvailabilityAppointmentAvailabilityMapper
import co.com.lafemmeapp.core.domain.params.GetAppointmentAvailabilityRequestParams
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.network.entities.AppointmentAvailabilityRequest
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.*
import javax.inject.Inject

/**
 * Created by oscargallon on 6/10/17.
 */
class GetAppointmentAvailabilitySOSUseCase(mSubscribeOnScheduler: Scheduler,
                                           mObserverOnScheduler: Scheduler) :
        UseCase<AppointmentAvailability, GetAppointmentAvailabilityRequestParams>(mSubscribeOnScheduler,
                mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(getAppointmentAvailabilityRequestParams:
                                        GetAppointmentAvailabilityRequestParams):
            Observable<AppointmentAvailability> {
        return mRepositoryFactory
                .valuesRepository
                .getValue(Constants.DBTOKEN_KEY)
                .flatMap { token ->
                    mRepositoryFactory
                            .appointmentRepository
                            .getAppointmentAvailabilitySOS(String.format(Locale.getDefault(),
                                    "%s %s", Constants.TOKEN_PREFIX,
                                    token),
                                    AppointmentAvailabilityRequest(getAppointmentAvailabilityRequestParams
                                            .lat,
                                            getAppointmentAvailabilityRequestParams.lng,
                                            getAppointmentAvailabilityRequestParams.appointmentServiceRequest,
                                            null, null))
                }.map(APIAppointmentAvailabilityAppointmentAvailabilityMapper.getInstance())
    }
}