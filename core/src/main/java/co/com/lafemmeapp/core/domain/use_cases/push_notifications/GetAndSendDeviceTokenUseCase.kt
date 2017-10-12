package co.com.lafemmeapp.core.domain.use_cases.push_notifications

import android.content.Context
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.local.DBDataSource
import co.com.lafemmeapp.dataprovider.network.entities.APIDeviceRegistrationResponse
import co.com.lafemmeapp.dataprovider.network.entities.DeviceRegisterParams
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import co.com.lafemmeapp.providers.ProviderFactory
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by oscargallon on 5/28/17.
 */
class GetAndSendDeviceTokenUseCase(subscribeOnScheduler: Scheduler?, observerOnScheduler: Scheduler?) :
        UseCase<Boolean, Context>(subscribeOnScheduler, observerOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(context: Context): Observable<Boolean> {
        return ProviderFactory.getInstance()
                .pushNotificacionRegisterProvider
                .getDeviceToken(context)
                .zipWith(mRepositoryFactory.sessionRepositoty.getSession()
                        .subscribeOn(Schedulers.computation()),
                        BiFunction<String, SessionResponse,
                                Pair<String, SessionResponse>> { deviceToken, sessionResponse ->
                            Pair(deviceToken, sessionResponse)
                        }).
                flatMap { (first, second) ->
                    Observable.create(ObservableOnSubscribe<Boolean> {
                        emitter ->
                        mRepositoryFactory
                                .deviceRepository.registerDevice("Bearer ${second
                                .token}",
                                DeviceRegisterParams(userUuid = second.uuid,
                                        deviceUuid = first,
                                        appIdentifier = "laFemme"))
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(object : DisposableObserver<APIDeviceRegistrationResponse>() {
                                    override fun onNext(value: APIDeviceRegistrationResponse?) {
                                        DBDataSource.getInstance()
                                                .put(Constants.DB_HAS_WATCH_ONBOARDING, "true")
                                        emitter.onNext(true)
                                        emitter.onComplete()
                                    }

                                    override fun onComplete() {
                                    }

                                    override fun onError(e: Throwable?) {
                                        emitter.onError(e)
                                    }

                                })
                    })
                }


    }
}