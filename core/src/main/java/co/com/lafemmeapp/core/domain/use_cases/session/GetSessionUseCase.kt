package co.com.lafemmeapp.core.domain.use_cases.session


import co.com.lafemmeapp.core.domain.Constants
import co.com.lafemmeapp.core.domain.entities.abstracts.User
import co.com.lafemmeapp.core.domain.mappers.SessionResponseCustomerMapper
import co.com.lafemmeapp.core.domain.mappers.SessionResponseSpecialitUserMapper
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.functions.Function
import javax.inject.Inject

/**
 * Created by oscargallon on 5/3/17.
 */

class GetSessionUseCase(mSubscribeOnScheduler: Scheduler, mObserverOnScheduler: Scheduler)
    : UseCase<User, Void?>(mSubscribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(o: Void?): Observable<User> {

       return  mRepositoryFactory.sessionRepositoty
                .getSession()
                .flatMap { sessionResponse ->
                    Observable
                            .create(ObservableOnSubscribe<Pair<Function<SessionResponse, User>,
                                    SessionResponse>> { emitter ->

                                val mapper: Function<SessionResponse, User> =
                                        if (sessionResponse.roles.contains(Constants.SPECIALIST_ROLE))
                                            SessionResponseSpecialitUserMapper.Companion.instance as Function<SessionResponse, User>
                                        else SessionResponseCustomerMapper.getInstace() as Function<SessionResponse, User>
                                emitter.onNext(Pair(mapper, sessionResponse))
                                emitter.onComplete()
                            })

                }.flatMap { (mapper, sessionResponse) ->
            Observable.create(ObservableOnSubscribe<User> { emitter ->
                emitter.onNext(mapper.apply(sessionResponse))
                emitter.onComplete()
            })
        }

    }
}
