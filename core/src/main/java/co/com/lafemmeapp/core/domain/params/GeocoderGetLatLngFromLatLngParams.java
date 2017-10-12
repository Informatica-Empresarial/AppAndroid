package co.com.lafemmeapp.core.domain.params;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by oscargallon on 4/27/17.
 */

public class GeocoderGetLatLngFromLatLngParams {

    private final WeakReference<Context> mContextWeakReference;

    private final String address;

    public GeocoderGetLatLngFromLatLngParams(Context context, String address) {
        mContextWeakReference = new WeakReference<Context>(context);
        this.address = address;
    }

    public WeakReference<Context> getmContextWeakReference() {
        return mContextWeakReference;
    }

    public String getAddress() {
        return address;
    }
}
