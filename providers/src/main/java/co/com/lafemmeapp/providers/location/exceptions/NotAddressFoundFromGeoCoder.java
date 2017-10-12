package co.com.lafemmeapp.providers.location.exceptions;

/**
 * Created by oscargallon on 4/25/17.
 */

public class NotAddressFoundFromGeoCoder extends Exception {

    @Override
    public String getMessage() {
        return "Not Address found for  the lat, lng provided";
    }
}
