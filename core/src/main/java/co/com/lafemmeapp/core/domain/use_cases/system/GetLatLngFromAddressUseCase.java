package co.com.lafemmeapp.core.domain.use_cases.system;

import com.google.android.gms.maps.model.LatLng;

import co.com.lafemmeapp.core.domain.params.GeocoderGetLatLngFromLatLngParams;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.providers.location.GeocoderFactory;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by oscargallon on 4/27/17.
 */

public class GetLatLngFromAddressUseCase extends UseCase<LatLng, GeocoderGetLatLngFromLatLngParams> {

    public GetLatLngFromAddressUseCase(Scheduler subscribeOnScheduler,
                                       Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<LatLng> buildUseCaseObservable(GeocoderGetLatLngFromLatLngParams
                                                             geocoderGetLatLngFromLatLngParams) {
        return GeocoderFactory.getInstance()
                .getmGeoCoderProviderInstance(geocoderGetLatLngFromLatLngParams
                        .getmContextWeakReference().get())
                .getLatLngFromAddress(geocoderGetLatLngFromLatLngParams.getAddress());
    }


}
