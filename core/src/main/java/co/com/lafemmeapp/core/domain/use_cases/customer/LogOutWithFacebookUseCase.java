package co.com.lafemmeapp.core.domain.use_cases.customer;

import com.facebook.AccessToken;

import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.dataprovider.network.GraphFacebookProvider;
import co.com.lafemmeapp.dataprovider.network.IGraphFacebookProvider;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by Stephys on 6/05/17.
 */

public class LogOutWithFacebookUseCase extends UseCase<Boolean, AccessToken> {

    private IGraphFacebookProvider mGraphFacebookProvider;

    public LogOutWithFacebookUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
        mGraphFacebookProvider = new GraphFacebookProvider();
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(AccessToken accessToken) {
        return mGraphFacebookProvider.LogOutFacebookUser(accessToken);
    }
}
