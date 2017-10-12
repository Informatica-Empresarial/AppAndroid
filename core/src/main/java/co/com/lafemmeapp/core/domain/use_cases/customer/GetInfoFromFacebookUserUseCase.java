package co.com.lafemmeapp.core.domain.use_cases.customer;

import com.facebook.AccessToken;

import co.com.lafemmeapp.core.domain.entities.FacebookUserData;
import co.com.lafemmeapp.core.domain.mappers.JsonObjectFacebookUserDataMapper;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.dataprovider.network.GraphFacebookProvider;
import co.com.lafemmeapp.dataprovider.network.IGraphFacebookProvider;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by oscar.gallon on 4/21/17.
 */

public class GetInfoFromFacebookUserUseCase extends UseCase<FacebookUserData, AccessToken> {

    private IGraphFacebookProvider mGraphFacebookProvider;

    public GetInfoFromFacebookUserUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
        mGraphFacebookProvider = new GraphFacebookProvider();
    }

    @Override
    public Observable<FacebookUserData> buildUseCaseObservable(final AccessToken token) {
        /**
         * So we can just connect the {@link IGraphFacebookProvider} result Observable
         * with a function to map the response to the {@link FacebookUserData} object
         */
        return mGraphFacebookProvider
                .getFacebookUserProfileInfo(token)
                .map(JsonObjectFacebookUserDataMapper.getInstance());
    }


}
