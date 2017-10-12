package co.com.lafemmeapp.core.domain.use_cases.push_notifications

import android.content.Context
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import co.com.lafemmeapp.dataprovider.params.DeleteDeviceRequest
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import co.com.lafemmeapp.providers.ProviderFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by oscargallon on 6/13/17.
 */
class DeleteDeviceUseCase(mSubscribeOnScheduler: Scheduler,
                          mObserverOnScheduler: Scheduler)
    : UseCase<Any, Context>(mSubscribeOnScheduler,
        mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: Context): Observable<Any> {
        return ProviderFactory.getInstance()
                .pushNotificacionRegisterProvider
                .getDeviceToken(params)
                .zipWith(mRepositoryFactory.sessionRepositoty.getSession()
                        .subscribeOn(Schedulers.computation()),
                        BiFunction<String, SessionResponse,
                                Pair<String, SessionResponse>> { deviceToken, sessionResponse ->
                            Pair(deviceToken, sessionResponse)
                        }).flatMap { (deviceToken, sessionResponse) ->

            mRepositoryFactory.deviceRepository
                    .deleteDevice("Bearer ${sessionResponse.token}",
                            DeleteDeviceRequest(sessionResponse.uuid,
                                    deviceToken))
        }
    }
}