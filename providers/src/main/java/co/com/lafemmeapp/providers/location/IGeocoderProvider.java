package co.com.lafemmeapp.providers.location;

import android.location.Address;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Observable;

/**
 * Created by oscargallon on 4/25/17.
 */

public interface IGeocoderProvider {


    Observable<Address> getAddressFromLatLng(@NonNull LatLng latLng);

    Observable<LatLng> getLatLngFromAddress(@NonNull String address);
}
