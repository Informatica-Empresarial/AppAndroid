package co.com.lafemmeapp.core.domain.use_cases;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by oscargallon on 4/21/17.
 */

public interface IUseCaseIterator<T, Params> {

    void execute(DisposableObserver<T> observer, Params params);


    void dispose();
}
