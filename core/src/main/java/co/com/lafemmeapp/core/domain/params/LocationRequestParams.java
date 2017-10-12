package co.com.lafemmeapp.core.domain.params;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by oscargallon on 4/25/17.
 */

public class LocationRequestParams {

    private final WeakReference<Context> contextWeakReference;

    private final String Provider;

    public LocationRequestParams(Context context, String provider) {
        contextWeakReference = new WeakReference<Context>(context);
        Provider = provider;
    }

    public WeakReference<Context> getContextWeakReference() {
        return contextWeakReference;
    }

    public String getProvider() {
        return Provider;
    }
}
