package co.com.lafemmeapp.core.domain.use_cases.specialist

import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by oscargallon on 6/9/17.
 */
class DeleteSOSActivationDateUseCase(mSubscribeOnScheduler: Scheduler,
                                     mObserverOnScheduler: Scheduler) :
        UseCase<Boolean, Void?>(mSubscribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: Void?): Observable<Boolean> {
        return Observable.create {
            emitter ->
            mRepositoryFactory.valuesRepository
                    .deleteKey(Constants.SOS_ACTIVATION_TIME)
            emitter.onNext(true)
            emitter.onComplete()

        }
    }
}