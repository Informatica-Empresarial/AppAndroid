package co.com.lafemmeapp.core.domain.use_cases.system;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import co.com.lafemmeapp.core.domain.params.VerifyAddressLatLngParams;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.providers.location.GeocoderFactory;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 5/8/17.
 */

public class VerifyAddressAndLatLngUseCase extends UseCase<Float, VerifyAddressLatLngParams> {

    public VerifyAddressAndLatLngUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<Float> buildUseCaseObservable(final VerifyAddressLatLngParams verifyAddressLatLngParams) {
        return GeocoderFactory.getInstance()
                .getmGeoCoderProviderInstance(verifyAddressLatLngParams.getmContextWeakReference()
                        .get())
                .getLatLngFromAddress(verifyAddressLatLngParams.getmAddress())
                .map(new Function<LatLng, Float>() {
                    @Override
                    public Float apply(LatLng latLng) throws Exception {
                        Location loc1 = new Location("");
                        loc1.setLatitude(latLng.latitude);
                        loc1.setLongitude(latLng.longitude);
                        Location loc2 = new Location("");
                        loc2.setLatitude(verifyAddressLatLngParams.getmLatLng().latitude);
                        loc2.setLongitude(verifyAddressLatLngParams.getmLatLng()
                                .longitude);
                        return loc1.distanceTo(loc2);
                    }
                });

    }
}
