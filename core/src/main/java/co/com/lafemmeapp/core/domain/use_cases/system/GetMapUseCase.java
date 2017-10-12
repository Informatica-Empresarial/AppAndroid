package co.com.lafemmeapp.core.domain.use_cases.system;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.providers.location.GoogleMapsAPI;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by oscargallon on 5/1/17.
 */

public class GetMapUseCase extends UseCase<GoogleMap, MapFragment> {
    public GetMapUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<GoogleMap> buildUseCaseObservable(MapFragment mapFragment) {
        return GoogleMapsAPI.getInstance()
                .getMapProvider().getGoogleMapProvider(mapFragment);
    }
}
