package co.com.lafemmeapp.core.domain.use_cases.system;

import javax.inject.Inject;

import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.core.domain.use_cases.UseCaseFactory;
import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.repo.RepositoryFactory;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;

/**
 * Created by Stephys on 7/05/17.
 */

public class SetHasWatchOnboardingUseCase extends UseCase<Boolean, Boolean> {

    @Inject
    IRepositoryFactory mRepositoryFactory;

    public SetHasWatchOnboardingUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);

    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(Boolean hasWatchOnboarding) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                mRepositoryFactory
                        .getValuesRepository()
                        .saveValue(Constants.DB_HAS_WATCH_ONBOARDING,
                                "true");
                emitter.onNext(true);
                emitter.onComplete();
            }
        });
    }
}