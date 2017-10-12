package co.com.lafemmeapp.core.domain.use_cases.specialist

import android.location.Location
import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.core.domain.mappers.APISpecialistUserSpecialistUser
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.network.entities.LastKnownLocation
import co.com.lafemmeapp.dataprovider.network.entities.UpdateSpecialistLocationRequest
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * Created by oscargallon on 6/5/17.
 */
class UpdateSpecialistLocationUseCase(mSubscribeOnScheduler: Scheduler,
                                      mObserverOnScheduler: Scheduler) :
        UseCase<SpecialistUser, Location>(mSubscribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: Location): Observable<SpecialistUser> {
        return mRepositoryFactory.sessionRepositoty
                .getSession()
                .flatMap { session ->
                    mRepositoryFactory.iParamsRepository
                            .updateSpecialistUserLocation("Bearer ${session.token}",
                                    session.uuid,
                                    UpdateSpecialistLocationRequest(LastKnownLocation(lat = params
                                            .latitude,
                                            lng = params.longitude)))
                }
                .map { apiSpecialistUser ->
                    APISpecialistUserSpecialistUser.getInstance()
                            .apply(apiSpecialistUser)
                }
    }
}