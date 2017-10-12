package co.com.lafemmeapp.providers.location.exceptions;

/**
 * Created by oscargallon on 4/25/17.
 */

public class NetworkProviderException extends Exception {

    @Override
    public String getMessage() {
        return "Network Provider not enabled or not found";
    }
}
