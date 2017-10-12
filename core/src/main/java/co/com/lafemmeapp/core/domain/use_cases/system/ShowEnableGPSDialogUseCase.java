package co.com.lafemmeapp.core.domain.use_cases.system;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.providers.location.LocationAPI;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by oscargallon on 5/2/17.
 */

public class ShowEnableGPSDialogUseCase extends UseCase<Status,GoogleApiClient> {

    public ShowEnableGPSDialogUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<Status> buildUseCaseObservable(GoogleApiClient googleApiClient) {
        return LocationAPI.getInstance()
                .showGPSDialog(googleApiClient);
    }
}
