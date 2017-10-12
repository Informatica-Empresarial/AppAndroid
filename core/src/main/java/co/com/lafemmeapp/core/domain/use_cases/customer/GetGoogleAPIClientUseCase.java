package co.com.lafemmeapp.core.domain.use_cases.customer;

import com.google.android.gms.common.api.GoogleApiClient;

import co.com.lafemmeapp.core.domain.params.GoogleAPIClientParams;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.providers.google_api_client.GoogleAPIClientAPI;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by oscargallon on 5/2/17.
 */

public class GetGoogleAPIClientUseCase extends UseCase<GoogleApiClient,
        GoogleAPIClientParams> {
    public GetGoogleAPIClientUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<GoogleApiClient> buildUseCaseObservable(GoogleAPIClientParams googleAPIClientParams) {
        return GoogleAPIClientAPI.getInstance()
                .getGoogleAPIClient(googleAPIClientParams.getContextWeakReference().get(),
                        googleAPIClientParams.getRequestAPI());
    }
}
