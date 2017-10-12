package co.com.lafemmeapp.core.domain.use_cases.specialist

import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.core.domain.mappers.APISpecialistUserSpecialistUser
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.network.entities.APISpecialistUser
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by oscargallon on 6/9/17.
 */
class TurnOffSOSUseCase(mSubscribeOnScheduler: Scheduler,
                        mObserverOnScheduler: Scheduler) : UseCase<SpecialistUser, Void?>(mSubscribeOnScheduler,
        mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: Void?): Observable<SpecialistUser?> {
        return mRepositoryFactory
                .sessionRepositoty
                .getSession()
                .flatMap { session ->
                    mRepositoryFactory
                            .iParamsRepository
                            .turnOffSOS("Bearer ${session.token}", session.uuid)
                }.flatMap { apiSpecialist ->
            Observable.create(ObservableOnSubscribe<APISpecialistUser> { emitter ->
                mRepositoryFactory.sessionRepositoty
                        .updateSOS(apiSpecialist.isSOS, apiSpecialist.uuid)
                emitter.onNext(apiSpecialist)
                emitter.onComplete()
            })
        }.map { apiSpecialist ->
            APISpecialistUserSpecialistUser.getInstance()
                    .apply(apiSpecialist)
        }
    }
}