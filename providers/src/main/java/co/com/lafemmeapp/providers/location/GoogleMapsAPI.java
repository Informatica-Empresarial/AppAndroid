package co.com.lafemmeapp.providers.location;

/**
 * Created by oscargallon on 5/1/17.
 */

public class GoogleMapsAPI {

    private static final GoogleMapsAPI instance = new GoogleMapsAPI();

    private IGoogleMapsProvider mGoogleMapsProvider;



    private GoogleMapsAPI() {

    }

    public static GoogleMapsAPI getInstance() {
        return instance;
    }

    public IGoogleMapsProvider getMapProvider() {
        if (mGoogleMapsProvider == null) {
            mGoogleMapsProvider = new GoogleMapsProvider();
        }

        return mGoogleMapsProvider;
    }
}
