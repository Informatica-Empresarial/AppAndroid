package co.com.lafemmeapp.core.domain.use_cases.session;

import javax.inject.Inject;

import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.dataprovider.repo.RepositoryFactory;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by oscargallon on 5/13/17.
 */

public class CloseSessionUseCase extends UseCase<Boolean, Void> {
    @Inject
    IRepositoryFactory mRepositoryFactory;

    public CloseSessionUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(Void aVoid) {
        return mRepositoryFactory
                .getSessionRepositoty()
                .deleteSession();
    }
}
