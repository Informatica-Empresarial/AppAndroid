package co.com.lafemmeapp.providers.location;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by oscargallon on 4/27/17.
 */

public class GeocoderFactory {
    private static final GeocoderFactory ourInstance = new GeocoderFactory();

    public static GeocoderFactory getInstance() {
        return ourInstance;
    }

    private GeocoderProvider mGeocoderProvider;

    private GeocoderFactory() {
    }

    public IGeocoderProvider getmGeoCoderProviderInstance(@NonNull Context context){
        if(mGeocoderProvider==null){
            mGeocoderProvider = new GeocoderProvider(context);
        }
        return mGeocoderProvider;
    }
}
