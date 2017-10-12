package co.com.lafemmeapp.providers.location.exceptions;

/**
 * Created by oscargallon on 4/25/17.
 */

public class GPSProviderException extends Exception {

    public GPSProviderException() {
    }

    @Override
    public String getMessage() {
        return "GPS Provider not enabled or not found";
    }
}
