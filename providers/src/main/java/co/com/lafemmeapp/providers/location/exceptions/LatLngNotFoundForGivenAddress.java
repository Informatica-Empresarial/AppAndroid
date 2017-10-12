package co.com.lafemmeapp.providers.location.exceptions;

/**
 * Created by oscargallon on 4/27/17.
 */

public class LatLngNotFoundForGivenAddress extends Exception {

    @Override
    public String getMessage() {
        return "No address found for the address given";
    }
}
