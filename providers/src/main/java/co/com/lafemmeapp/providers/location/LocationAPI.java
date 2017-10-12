package co.com.lafemmeapp.providers.location;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by oscargallon on 4/25/17.
 */

public class LocationAPI implements ILocationAPI {
    private static final LocationAPI ourInstance = new LocationAPI();

    public static LocationAPI getInstance() {
        return ourInstance;
    }

    private ILocationProvider mLocationProvider;

    private LocationAPI() {
    }

    @Override
    public Observable<Location> getLocation(final Context context, final String provider) {
        return Observable.create(new ObservableOnSubscribe<Location>() {
            @Override
            public void subscribe(ObservableEmitter<Location> emitter) throws Exception {
                mLocationProvider = new LocationProvider();
                mLocationProvider.getUserLocation(context, emitter, provider);

            }
        });
    }

    @Override
    public void cancelObservable() {
        if (mLocationProvider != null) {
            mLocationProvider.removeLocationEmmiter();
        }
    }

    @Override
    public Observable<Status> showGPSDialog(final GoogleApiClient googleApiClient) {
        return Observable.create(new ObservableOnSubscribe<Status>() {
            @Override
            public void subscribe(ObservableEmitter<Status> emitter) throws Exception {
                if (mLocationProvider == null) {
                    mLocationProvider = new LocationProvider();
                }

                mLocationProvider.showGPSDialog(googleApiClient, emitter);
            }

        });
    }


}
