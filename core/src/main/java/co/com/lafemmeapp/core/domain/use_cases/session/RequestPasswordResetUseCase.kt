package co.com.lafemmeapp.core.domain.use_cases.session

import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by oscargallon on 6/13/17.
 */
class RequestPasswordResetUseCase(mSubscribeOnScheduler: Scheduler,
                                  mObserverOnScheduler: Scheduler)
    : UseCase<String, String>(mSubscribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: String): Observable<String> {
        return mRepositoryFactory.sessionRepositoty
                .requestPasswordReset(params)
    }
}