package co.com.lafemmeapp.providers.google_api_client;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

import co.com.lafemmeapp.providers.location.exceptions.GoogleAPIClientConnectionFailiur;
import io.reactivex.ObservableEmitter;

/**
 * Created by oscargallon on 5/2/17.
 */

public class GoogleAPIClienteProvider implements IGoogleAPIClienteProvider {

    private GoogleApiClient mGoogleApiClient;

    private static ObservableEmitter<GoogleApiClient> mGoogleApiClientObservableEmitter;

    @Override
    @SuppressWarnings("unchecked")
    public void buildGoogleApiClient(Context context, Api requestAPI,
                                     ObservableEmitter<GoogleApiClient> emitter) {
        mGoogleApiClientObservableEmitter = emitter;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(requestAPI)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mGoogleApiClient != null && mGoogleApiClientObservableEmitter != null) {
            mGoogleApiClientObservableEmitter.onNext(mGoogleApiClient);
            mGoogleApiClientObservableEmitter.onComplete();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mGoogleApiClientObservableEmitter != null) {
            mGoogleApiClientObservableEmitter.onError(new GoogleAPIClientConnectionFailiur(connectionResult
                    .getErrorMessage()));
        }
    }
}
