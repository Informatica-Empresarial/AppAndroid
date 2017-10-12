package co.com.lafemmeapp.providers.google_api_client;

import android.content.Context;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

import io.reactivex.ObservableEmitter;

/**
 * Created by oscargallon on 5/2/17.
 */

public interface IGoogleAPIClienteProvider extends GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    void buildGoogleApiClient(Context context, Api requestAPI,
                              ObservableEmitter<GoogleApiClient> emitter);
}
