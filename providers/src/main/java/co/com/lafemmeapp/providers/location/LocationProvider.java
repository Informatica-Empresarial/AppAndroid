package co.com.lafemmeapp.providers.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import co.com.lafemmeapp.providers.location.exceptions.GPSProviderException;
import co.com.lafemmeapp.providers.location.exceptions.NetworkProviderException;
import co.com.lafemmeapp.providers.location.exceptions.NoSuchAsProviderException;
import io.reactivex.ObservableEmitter;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by oscargallon on 4/25/17.
 */

public class LocationProvider implements ILocationProvider, LocationListener {


    private static ObservableEmitter<Location> mLocationObservableEmitter;

    private static LocationManager mLocationManager;

    private static final LocationProvider instance = new LocationProvider();

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;

    private static final long MIN_TIME_UPDATES = 0;

    private static Looper mLooper;

    public LocationProvider() {

    }


    @Override
    public void getUserLocation(@NonNull Context context,
                                ObservableEmitter<Location> locationObservableEmitter,
                                final String provider) {
        mLocationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);

        mLocationObservableEmitter = locationObservableEmitter;
        mLooper = Looper.myLooper() == null ?
                Looper.getMainLooper() : Looper.myLooper();

        if (provider == null || (!provider.equals(LocationManager.GPS_PROVIDER)
                && !provider.equals(LocationManager.NETWORK_PROVIDER))) {
            if (mLocationObservableEmitter != null) {
                mLocationObservableEmitter.onError(new NoSuchAsProviderException());
            }
            return;
        }


        if (mLocationManager == null) {
            mLocationObservableEmitter.onError(new RuntimeException("Something error"));
            mLocationObservableEmitter.onComplete();
        }


        switch (provider) {
            case LocationManager.GPS_PROVIDER: {
                if (!isGPSEnabled()) {
                    if (mLocationObservableEmitter != null) {
                        mLocationObservableEmitter.onError(new GPSProviderException());
                    }
                    return;
                }

                break;
            }
            case LocationManager.NETWORK_PROVIDER: {
                if (!isNetworkEnabled()) {
                    if (mLocationObservableEmitter != null) {
                        mLocationObservableEmitter.onError(new NetworkProviderException());
                    }
                    return;
                }
                break;
            }
        }


        if (mLocationManager != null) {


            new Handler(mLooper).post(new Runnable() {
                @Override
                public void run() {
                    mLocationManager.requestLocationUpdates(provider, MIN_TIME_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, LocationProvider.this);
                }
            });
        }

        if (mLocationManager.getLastKnownLocation(provider) != null && mLocationObservableEmitter != null) {
            mLocationObservableEmitter.onNext(mLocationManager.getLastKnownLocation(provider));
        }
    }


    @Override
    public void removeLocationEmmiter() {
        if (mLocationObservableEmitter != null) {
            mLocationObservableEmitter.onComplete();
            mLocationObservableEmitter = null;
        }

        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);

        }
    }

    private boolean isGPSEnabled() {
        return mLocationManager != null && mLocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean isNetworkEnabled() {
        return mLocationManager != null && mLocationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    @Override
    public void onLocationChanged(Location location) {
        if (mLocationObservableEmitter != null) {
            mLocationObservableEmitter.onNext(location);
            mLocationObservableEmitter.onComplete();
            if (mLocationManager != null) {
                mLocationManager.removeUpdates(this);
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("PROVIDEr", "DISABLE");
    }

    @Override
    public void showGPSDialog(final GoogleApiClient googleApiClient,
                              final ObservableEmitter<Status> emitter) {


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        final PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                emitter.onNext(locationSettingsResult.getStatus());
                emitter.onComplete();
            }
        });


    }
}
