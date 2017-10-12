package co.com.lafemmeapp.core.domain.use_cases.system;

import android.location.Location;

import co.com.lafemmeapp.core.domain.params.LocationRequestParams;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.providers.location.LocationAPI;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by oscargallon on 4/25/17.
 */

public class GetUserLocationUseCase extends UseCase<Location, LocationRequestParams> {


    private final Scheduler mObserverOnScheduler;

    public GetUserLocationUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
        mObserverOnScheduler = observerOnScheduler;
    }

    @Override
    public Observable<Location> buildUseCaseObservable(LocationRequestParams locationRequestParams) {
        return LocationAPI.getInstance().getLocation(locationRequestParams
                        .getContextWeakReference().get(),
                locationRequestParams.getProvider());
    }
}
