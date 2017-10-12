package co.com.lafemmeapp.providers.location;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

import io.reactivex.ObservableEmitter;

/**
 * Created by oscargallon on 4/25/17.
 */

public interface ILocationProvider {

    void getUserLocation(@NonNull Context context,
                         ObservableEmitter<Location> locationObservableEmitter,
                         final String provider);

    void removeLocationEmmiter();

    void showGPSDialog(final GoogleApiClient googleApiClient,
                       final ObservableEmitter<Status> emitter);
}
