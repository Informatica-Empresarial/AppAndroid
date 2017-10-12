package co.com.lafemmeapp.core.domain.use_cases.system;

import android.location.Address;

import java.util.Locale;

import co.com.lafemmeapp.core.domain.params.GeocoderGetAddressFromLatLngParams;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.providers.location.GeocoderFactory;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 4/25/17.
 */

public class GetAddressFromLatLngUseCase extends UseCase<String, GeocoderGetAddressFromLatLngParams> {

    public GetAddressFromLatLngUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<String> buildUseCaseObservable(GeocoderGetAddressFromLatLngParams geocoderGetAddressFromLatLngParams) {
        return GeocoderFactory.getInstance()
                .getmGeoCoderProviderInstance(geocoderGetAddressFromLatLngParams.getmContextWeakReference().get())
                .getAddressFromLatLng(geocoderGetAddressFromLatLngParams.getmLatLng())
                .map(new Function<Address, String>() {
                    @Override
                    public String apply(Address address) throws Exception {
                        String addressString = "";
                        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                            addressString += String.format(Locale.getDefault(),
                                    "%s ", address.getAddressLine(i));

                        }


                        return addressString;
                    }
                });
    }
}
