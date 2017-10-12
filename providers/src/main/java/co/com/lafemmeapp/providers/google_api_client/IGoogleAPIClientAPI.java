package co.com.lafemmeapp.providers.google_api_client;

import android.content.Context;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

import io.reactivex.Observable;

/**
 * Created by oscargallon on 5/2/17.
 */

public interface IGoogleAPIClientAPI {

    Observable<GoogleApiClient> getGoogleAPIClient(final Context context, final Api api);
}
