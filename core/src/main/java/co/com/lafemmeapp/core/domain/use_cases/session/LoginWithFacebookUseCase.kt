package co.com.lafemmeapp.core.domain.use_cases.session

import co.com.lafemmeapp.core.domain.mappers.DBSessionResponseSessionResponseMapper
import co.com.lafemmeapp.core.domain.mappers.SessionResponseDBSessionResponseMapper
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.*
import javax.inject.Inject

/**
 * Created by oscargallon on 6/13/17.
 */
class LoginWithFacebookUseCase(mSubscribeOnScheduler: Scheduler,
                               mObserverOnScheduler: Scheduler)
    : UseCase<SessionResponse, String>(mSubscribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: String): Observable<SessionResponse> {
        return mRepositoryFactory
                .sessionRepositoty
                .loginWithFacebook(params)
                .flatMap { sessionResponse ->
                    mRepositoryFactory
                            .sessionRepositoty
                            .storeSession(SessionResponseDBSessionResponseMapper.instance
                                    .apply(sessionResponse))

                }.map { dbSessionResponse ->
            DBSessionResponseSessionResponseMapper.instance
                    .apply(dbSessionResponse)
        }.flatMap { sessionResponse ->
            Observable.create(ObservableOnSubscribe<SessionResponse> {
                emitter ->
                mRepositoryFactory.valuesRepository
                        .saveValue(Constants.DBTOKEN_KEY,
                                sessionResponse.token)
                emitter.onNext(sessionResponse)
                emitter.onComplete()
            })
        }


    }
}