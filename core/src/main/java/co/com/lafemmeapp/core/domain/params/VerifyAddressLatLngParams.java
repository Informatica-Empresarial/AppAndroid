package co.com.lafemmeapp.core.domain.params;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;

/**
 * Created by oscargallon on 5/8/17.
 */

public class VerifyAddressLatLngParams {

    private final LatLng mLatLng;

    private final String mAddress;

    private WeakReference<Context> mContextWeakReference;

    public VerifyAddressLatLngParams(LatLng mLatLng, String address, Context context) {
        this.mLatLng = mLatLng;
        this.mAddress = address;
        mContextWeakReference = new WeakReference<Context>(context);
    }

    public LatLng getmLatLng() {
        return mLatLng;
    }

    public String getmAddress() {
        return mAddress;
    }

    public WeakReference<Context> getmContextWeakReference() {
        return mContextWeakReference;
    }

    public void setmContextWeakReference(WeakReference<Context> mContextWeakReference) {
        this.mContextWeakReference = mContextWeakReference;
    }
}
