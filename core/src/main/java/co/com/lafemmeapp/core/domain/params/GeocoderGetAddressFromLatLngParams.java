package co.com.lafemmeapp.core.domain.params;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;

/**
 * Created by oscargallon on 4/25/17.
 */

public class GeocoderGetAddressFromLatLngParams {

    private final WeakReference<Context> mContextWeakReference;

    private final LatLng mLatLng;

    public GeocoderGetAddressFromLatLngParams(Context context, LatLng latLng) {
        mContextWeakReference = new WeakReference<Context>(context);
        mLatLng = latLng;
    }

    public WeakReference<Context> getmContextWeakReference() {
        return mContextWeakReference;
    }

    public LatLng getmLatLng() {
        return mLatLng;
    }
}
