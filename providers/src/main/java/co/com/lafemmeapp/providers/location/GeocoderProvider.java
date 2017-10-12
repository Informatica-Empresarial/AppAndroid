package co.com.lafemmeapp.providers.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

import co.com.lafemmeapp.providers.location.exceptions.GeoCoderNotFoundOrNull;
import co.com.lafemmeapp.providers.location.exceptions.LatLngNotFoundForGivenAddress;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by oscargallon on 4/25/17.
 */

public class GeocoderProvider implements IGeocoderProvider {

    private Geocoder mGeocoder;


    public GeocoderProvider(@NonNull Context context) {
        mGeocoder = new Geocoder(context, Locale.getDefault());
    }

    @Override
    public Observable<Address> getAddressFromLatLng(@NonNull final LatLng latLng) {
        return Observable.create(new ObservableOnSubscribe<Address>() {
            @Override
            public void subscribe(ObservableEmitter<Address> emitter) throws Exception {
                if (mGeocoder != null) {

                    List<Address> addressList =
                            mGeocoder.getFromLocation(latLng.latitude,
                                    latLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0 &&
                            addressList.get(0).getMaxAddressLineIndex()!=-1) {
                        emitter.onNext(addressList.get(0));
                        emitter.onComplete();
                    } else {
                        emitter.onError(new GeoCoderNotFoundOrNull());
                    }


                } else {
                    emitter.onError(new GeoCoderNotFoundOrNull());
                }
            }
        });
    }

    @Override
    public Observable<LatLng> getLatLngFromAddress(@NonNull final String address) {
        return Observable.create(new ObservableOnSubscribe<LatLng>() {
            @Override
            public void subscribe(ObservableEmitter<LatLng> emitter) throws Exception {
                if (mGeocoder != null) {

                    List<Address> addressList =
                            mGeocoder.getFromLocationName(address, 1);
                    if (addressList != null && addressList.size() > 0) {
                        emitter.onNext(new LatLng(addressList.get(0)
                                .getLatitude(), addressList.get(0).getLongitude()));
                        emitter.onComplete();
                    } else if (addressList != null && addressList.size() == 0) {
                        emitter.onError(new LatLngNotFoundForGivenAddress());
                    } else {
                        emitter.onError(new GeoCoderNotFoundOrNull());
                    }


                } else {
                    emitter.onError(new GeoCoderNotFoundOrNull());
                }
            }
        });
    }
}
