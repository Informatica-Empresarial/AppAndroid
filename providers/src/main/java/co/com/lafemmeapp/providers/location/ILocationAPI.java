package co.com.lafemmeapp.providers.location;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

import io.reactivex.Observable;

/**
 * Created by oscargallon on 4/25/17.
 */

public interface ILocationAPI {

    Observable<Location> getLocation(Context context, String provider);

    void cancelObservable();

    Observable<Status> showGPSDialog(GoogleApiClient googleApiClient);
}
