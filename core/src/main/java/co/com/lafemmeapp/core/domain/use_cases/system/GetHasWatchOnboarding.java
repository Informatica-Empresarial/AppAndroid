package co.com.lafemmeapp.core.domain.use_cases.system;

import javax.inject.Inject;

import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.core.domain.use_cases.UseCaseFactory;
import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.repo.ParamsRepository;
import co.com.lafemmeapp.dataprovider.repo.RepositoryFactory;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Stephys on 7/05/17.
 */

public class GetHasWatchOnboarding extends UseCase<Boolean, Void> {

    @Inject
    IRepositoryFactory mRepositoryFactory;

    public GetHasWatchOnboarding(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);

    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(Void aVoid) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                mRepositoryFactory
                        .getValuesRepository()
                        .getValue(Constants.DB_HAS_WATCH_ONBOARDING)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(Schedulers.computation())
                        .subscribe(new DisposableObserver<String>() {
                            @Override
                            public void onNext(String s) {
                                emitter.onNext(true);
                                emitter.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                emitter.onNext(false);
                                emitter.onComplete();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }
}