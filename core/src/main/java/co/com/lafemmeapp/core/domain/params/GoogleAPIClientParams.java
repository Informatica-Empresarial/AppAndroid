package co.com.lafemmeapp.core.domain.params;

import android.content.Context;

import com.google.android.gms.common.api.Api;

import java.lang.ref.WeakReference;

/**
 * Created by oscargallon on 5/2/17.
 */

public class GoogleAPIClientParams {

    private final Api requestAPI;

    private final WeakReference<Context> contextWeakReference;

    public GoogleAPIClientParams(Api requestAPI, Context context) {
        this.requestAPI = requestAPI;
        this.contextWeakReference = new WeakReference<Context>(context);
    }

    public Api getRequestAPI() {
        return requestAPI;
    }

    public WeakReference<Context> getContextWeakReference() {
        return contextWeakReference;
    }
}
