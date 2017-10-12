package co.com.lafemmeapp.core.domain.use_cases.session

import co.com.lafemmeapp.core.domain.Constants
import co.com.lafemmeapp.core.domain.entities.abstracts.User
import co.com.lafemmeapp.core.domain.mappers.SessionResponseCustomerMapper
import co.com.lafemmeapp.core.domain.mappers.SessionResponseSpecialitUserMapper
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import co.com.lafemmeapp.dataprovider.params.EditProfileParams
import co.com.lafemmeapp.dataprovider.params.EditProfileRequest
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by oscargallon on 6/15/17.
 */
class UpdateUserUseCase(mSubscribeOnScheduler: Scheduler,
                        mObserverOnScheduler: Scheduler)
    : UseCase<User, EditProfileParams>(mSubscribeOnScheduler,
        mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory


    override fun buildUseCaseObservable(params: EditProfileParams): Observable<User> {
        return mRepositoryFactory
                .sessionRepositoty
                .getSession()
                .flatMap { session ->
                    mRepositoryFactory
                            .sessionRepositoty
                            .updateUser("Bearer ${session.token}",
                                    EditProfileRequest(params.mEmail,
                                            params.mPhoneNumber, session.uuid))
                }.flatMap { sessionResponse ->
            Observable.create(ObservableOnSubscribe<SessionResponse> { emitter ->

                mRepositoryFactory.sessionRepositoty
                        .updatePhoneNumber(sessionResponse.phoneNumber,
                                sessionResponse.uuid)
                mRepositoryFactory.sessionRepositoty
                        .updateEmail( sessionResponse.email,
                                sessionResponse.uuid)
                emitter.onNext(sessionResponse)


            })
        }.map { sessionResponse ->
                    if (sessionResponse.roles !== null
                            && sessionResponse.roles.contains(Constants.SPECIALIST_ROLE)) {
                        SessionResponseSpecialitUserMapper.instance
                                .apply(sessionResponse)
                    } else {
                        SessionResponseCustomerMapper
                                .getInstace()
                                .apply(sessionResponse)
                    }

                }
    }
}