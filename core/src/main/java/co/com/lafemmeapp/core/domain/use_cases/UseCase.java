package co.com.lafemmeapp.core.domain.use_cases;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by oscargallon on 4/8/17.
 */

public abstract class UseCase<T, Params> implements IUseCaseIterator<T, Params> {


    private final Scheduler subscribeOnScheduler;

    private final Scheduler observerOnScheduler;

    private final CompositeDisposable disposables;

    public UseCase(Scheduler subscribeOnScheduler,
                   Scheduler observerOnScheduler) {
        this.subscribeOnScheduler = subscribeOnScheduler;
        this.observerOnScheduler = observerOnScheduler;
        this.disposables = new CompositeDisposable();

    }


    public abstract Observable<T> buildUseCaseObservable(Params params);


    @Override
    public void execute(DisposableObserver<T> observer, Params params) {
        final Observable<T> observable = this.buildUseCaseObservable(params)
                .observeOn(observerOnScheduler)
                .subscribeOn(subscribeOnScheduler);

        addDisposable(observable.subscribeWith(observer));
    }



    @Override
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }


    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }


}
