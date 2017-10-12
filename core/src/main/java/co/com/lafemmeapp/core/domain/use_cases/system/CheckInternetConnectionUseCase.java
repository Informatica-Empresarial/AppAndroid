package co.com.lafemmeapp.core.domain.use_cases.system;

import java.net.InetAddress;

import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;

/**
 * Created by oscargallon on 5/13/17.
 */

public class CheckInternetConnectionUseCase extends UseCase<Boolean, Void> {

    public CheckInternetConnectionUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(Void aVoid) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                InetAddress ipAddr = InetAddress.getByName("www.google.com");
                emitter.onNext(!ipAddr.getHostName().equals(""));
                emitter.onComplete();
            }
        });
    }
}
