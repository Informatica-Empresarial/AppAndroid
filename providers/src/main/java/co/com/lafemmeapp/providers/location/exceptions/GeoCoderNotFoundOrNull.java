package co.com.lafemmeapp.providers.location.exceptions;

/**
 * Created by oscargallon on 4/25/17.
 */

public class GeoCoderNotFoundOrNull extends Exception {

    @Override
    public String getMessage() {
        return "GEOcoder not found or null";
    }
}
