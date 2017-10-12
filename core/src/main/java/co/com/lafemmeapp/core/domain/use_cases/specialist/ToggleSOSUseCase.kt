package co.com.lafemmeapp.core.domain.use_cases.specialist

import android.location.Location
import android.location.LocationManager
import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.core.domain.entities.abstracts.User
import co.com.lafemmeapp.core.domain.mappers.APISpecialistUserSpecialistUser
import co.com.lafemmeapp.core.domain.params.LocationRequestParams
import co.com.lafemmeapp.core.domain.params.ToggleSOSParams
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.core.domain.use_cases.system.GetUserLocationUseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.network.entities.LastKnownLocation
import co.com.lafemmeapp.dataprovider.network.entities.UpdateSpecialistLocationRequest
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by oscargallon on 6/5/17.
 */
class ToggleSOSUseCase(mSubscribeOnScheduler: Scheduler, mObserverOnScheduler: Scheduler)
    : UseCase<SpecialistUser, ToggleSOSParams>(mSubscribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory


    override fun buildUseCaseObservable(params: ToggleSOSParams): Observable<SpecialistUser> {
        return GetUserLocationUseCase(Schedulers.computation(),
                Schedulers.newThread())
                .buildUseCaseObservable(LocationRequestParams(params.mContext, LocationManager.NETWORK_PROVIDER))
                .zipWith(params.mGetSessionUseCase
                        .buildUseCaseObservable(null).subscribeOn(Schedulers.computation()), BiFunction<Location, User,
                        Pair<Location, User>> { location,
                                                sessionResponse ->
                    Pair(location, sessionResponse)
                }).zipWith(mRepositoryFactory.valuesRepository.getValue(Constants.DBTOKEN_KEY)
                .subscribeOn(Schedulers.computation()),
                BiFunction<Pair<Location, User>, String, Triple<Location, User, String>> {
                    (first, second), token: String ->
                    Triple(first, second, token)
                }).flatMap { (location, user, token) ->
            mRepositoryFactory.iParamsRepository
                    .toggleSOS("Bearer $token",
                            user.uuid,
                            UpdateSpecialistLocationRequest(LastKnownLocation(lat = location.latitude,
                                    lng = location.longitude)))
                    .subscribeOn(Schedulers.io())
        }.map { apiSpecialistUser ->
            APISpecialistUserSpecialistUser.getInstance()
                    .apply(apiSpecialistUser)
        }.flatMap { specialistUser ->
            Observable.create(ObservableOnSubscribe<SpecialistUser> { emitter ->
                mRepositoryFactory.sessionRepositoty
                        .updateSOS(specialistUser.isSOS,
                                specialistUser.uuid)
                if (specialistUser.isSOS) {
                    mRepositoryFactory.valuesRepository
                            .saveValue(Constants.SOS_ACTIVATION_TIME,
                                    Calendar.getInstance().timeInMillis
                                            .toString())
                }
                emitter.onNext(specialistUser)
                emitter.onComplete()
            })
        }


    }
}