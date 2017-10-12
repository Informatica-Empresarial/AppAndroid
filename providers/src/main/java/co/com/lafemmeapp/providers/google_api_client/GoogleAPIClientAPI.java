package co.com.lafemmeapp.providers.google_api_client;

import android.content.Context;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by oscargallon on 5/2/17.
 */

public class GoogleAPIClientAPI {

    private static final GoogleAPIClientAPI instance = new GoogleAPIClientAPI();

    private GoogleAPIClientAPI() {

    }

    public static GoogleAPIClientAPI getInstance() {
        return instance;
    }

    public Observable<GoogleApiClient> getGoogleAPIClient(final Context context, final Api api) {
        return Observable.create(new ObservableOnSubscribe<GoogleApiClient>() {
            @Override
            public void subscribe(ObservableEmitter<GoogleApiClient> emitter) throws Exception {
                IGoogleAPIClienteProvider mGoogleAPIClienteProvider =
                        new GoogleAPIClienteProvider();
                mGoogleAPIClienteProvider.buildGoogleApiClient(context, api,
                        emitter);
            }
        });
    }

}
