package co.com.lafemmeapp.core.domain.use_cases.specialist


import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by oscargallon on 6/5/17.
 */
class IsOnSOSModeUseCase(mSubscribeOnScheduler: Scheduler,
                         mObserverOnScheduler: Scheduler) : UseCase<String, Void>(mSubscribeOnScheduler,
        mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: Void?): Observable<String> {
        return mRepositoryFactory
                .valuesRepository
                .getValue(Constants.SOS_ACTIVATION_TIME)
    }
}